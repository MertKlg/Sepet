package com.mk.sepetandroid.presentation.order.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.mk.sepetandroid.presentation.order.OrderEvent
import com.mk.sepetandroid.presentation.order.OrderState
import com.mk.sepetandroid.presentation.order.view.cancel_order.CancelOrder
import com.mk.sepetandroid.presentation.order.view.order_card_item.OrderCartItem
import com.mk.sepetandroid.presentation.order.view.order_change_address.OrderChangeAddress
import com.mk.sepetandroid.presentation.order.view.order_top_bar.OrderTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderView(
    state : OrderState,
    onEvent : (OrderEvent) -> Unit,
    navHostController : NavHostController
) {
    val snackbarHostState = SnackbarHostState()
    val scope = rememberCoroutineScope()


    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            OrderTopBar(
                navHostController
            )
        }
    ){ it ->

        LazyColumn(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding()
                )
                .fillMaxSize()
        ) {
            items(state.orders){
                OrderCartItem(
                    order = it,
                    state = state,
                    onEvent
                )
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        onEvent(OrderEvent.init)
    }

    OrderChangeAddress(state, onEvent)
    CancelOrder(state,onEvent)




    LaunchedEffect(key1 = state.isError) {
        if(state.isError){
            scope.launch {
                val stat = snackbarHostState.showSnackbar(
                    state.message,
                    withDismissAction = true
                )

                if(stat == SnackbarResult.Dismissed){
                    onEvent(OrderEvent.CloseDialog)
                }
            }
        }
    }
}