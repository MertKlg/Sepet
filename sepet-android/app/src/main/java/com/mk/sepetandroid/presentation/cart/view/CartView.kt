package com.mk.sepetandroid.presentation.cart.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mk.sepetandroid.R
import com.mk.sepetandroid.common.Constants
import com.mk.sepetandroid.presentation.cart.CartEvent
import com.mk.sepetandroid.presentation.cart.CartState
import com.mk.sepetandroid.presentation.widget.NotFound
import com.mk.sepetandroid.presentation.widget.SepetButton
import com.mk.sepetandroid.presentation.widget.SepetLoading
import com.mk.sepetandroid.presentation.widget.SepetText
import com.mk.sepetandroid.presentation.widget.SepetTopBack
import com.mk.sepetandroid.presentation.widget.SepetTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartView(
    onEvent : (CartEvent) -> Unit,
    state : CartState
) {
    val pagerState = rememberPagerState(pageCount = {2})
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            SepetTopBar(
                title = {
                    SepetText(
                        "Cart"
                    )
                },
                navigationIcon = {
                    when(pagerState.currentPage){
                        1 -> {
                            IconButton(
                                onClick = {
                                    scope.launch { pagerState.animateScrollToPage(0) }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
                                    contentDescription = "Back to the cart"
                                )
                            }
                        }
                    }

                }
            )
        }
    ){
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
        ) {
            when(it){
                0 -> {
                    CartProducts(
                        cart = state.cart,
                        onEvent = onEvent,
                        takeOrder = {
                        scope.launch {
                            nextPage(pagerState)
                        }
                    })
                }

                1 -> {
                    CartTakeOrder(
                        cartModel = state.cart,
                        address = state.address,
                        onEvent = onEvent,
                    )
                }
            }
        }

    }

    LaunchedEffect(key1 = Unit){
        onEvent(CartEvent.GetCart)
        onEvent(CartEvent.GetAddress)
    }

    LaunchedEffect(key1 = state.isMessageAvailable) {
        if(state.isMessageAvailable){
            scope.launch {
                val res = snackbarHostState.showSnackbar(state.message ?: "Something went wrong", withDismissAction = true)

                when(res){
                    SnackbarResult.Dismissed -> {
                        onEvent(CartEvent.ClearMessage)
                    }

                    SnackbarResult.ActionPerformed -> {}
                }
            }
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
private suspend fun nextPage(pagerState : PagerState) {
    pagerState.animateScrollToPage(
        if (pagerState.currentPage == (pagerState.pageCount - 1)) 0 else pagerState.currentPage + 1,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow
        )
    )
}