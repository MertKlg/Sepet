package com.mk.sepetandroid.presentation.order.view.cancel_order

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.sepetandroid.domain.model.order.OrderStatTypes
import com.mk.sepetandroid.domain.model.order.UpdateOrderModel
import com.mk.sepetandroid.presentation.order.OrderEvent
import com.mk.sepetandroid.presentation.order.OrderState
import com.mk.sepetandroid.presentation.widget.SepetText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelOrder(
    state : OrderState,
    onEvent: (OrderEvent) -> Unit
){
    AnimatedVisibility(state.cancelDialog) {
        BasicAlertDialog(
            onDismissRequest = {
                onEvent(
                    OrderEvent.ShowCancelDialog(false)
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.9F)
                    .fillMaxHeight(.2F)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(15.dp)
            ) {
                SepetText(
                    text = "Are you sure ?",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                SepetText(
                    text = "Are you sure cancel order",
                    fontWeight = FontWeight.Thin
                )

                Row(
                    modifier = Modifier
                        .fillMaxSize(),

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    TextButton(
                        onClick = {
                            onEvent(
                                OrderEvent.ShowCancelDialog(false)
                            )
                        },

                        content = {
                            SepetText(
                                text = "No"
                            )
                        }
                    )

                    TextButton(
                        onClick = {
                            onEvent(
                                OrderEvent.UpdateOrder(
                                    state.selectedOrder,
                                    UpdateOrderModel(
                                        orderStatus = OrderStatTypes.ORDER_CANCELED.name
                                    )
                                )
                            )

                            onEvent(
                                OrderEvent.ShowCancelDialog(false)
                            )
                        },

                        content = {
                            SepetText(
                                text = "Yes"
                            )
                        }
                    )
                }

            }
        }
    }
}