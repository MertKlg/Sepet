package com.mk.sepetandroid.presentation.address.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.mk.sepetandroid.R
import com.mk.sepetandroid.presentation.address.AddressEvent
import com.mk.sepetandroid.presentation.address.AddressState
import com.mk.sepetandroid.presentation.address.view.add_or_update_address.AddOrUpdateAddress
import com.mk.sepetandroid.presentation.address.view.address_item.AddressItem
import com.mk.sepetandroid.presentation.widget.SepetText
import com.mk.sepetandroid.presentation.widget.SepetTopBar
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddressView(
    navHostController : NavHostController,
    state : AddressState,
    onEvent : (AddressEvent) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {2})
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(key1 = Unit) {
        onEvent(AddressEvent.GetAddress)
    }

    LaunchedEffect(key1 = state.onStatus) {
        if(state.onStatus){
            scope.launch {
                snackbarHostState.showSnackbar(state.message ?: "", withDismissAction = true)
            }
        }
    }


    Scaffold(
        snackbarHost = {
          SnackbarHost(snackbarHostState)
        },
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SepetTopBar(
                title = {
                    SepetText(
                        text = "Address"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if(pagerState.currentPage == 0){
                                navHostController.navigateUp()
                            }else{
                                onEvent(
                                    AddressEvent.WillClearUpdateAddress
                                )
                                scope.launch {
                                    pagerState.animateScrollToPage(0)
                                }
                            }

                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                                contentDescription = "",
                            )
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                nextPage(pagerState)
                            }
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_save_24),
                                contentDescription = "",
                            )
                        },
                    )
                }
            )
        },
        content = { it ->
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                when(page){
                    0 -> {
                        AddressItem(
                            state,
                            it.calculateTopPadding(),
                            onEvent,
                            nextPage = {
                                scope.launch {
                                    nextPage(pagerState)
                                }
                            }
                        )
                    }

                    1 -> {
                        AddOrUpdateAddress(
                            onEvent,
                            state,
                            it.calculateTopPadding()
                        )
                    }
                }
            }

        }
    )

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
