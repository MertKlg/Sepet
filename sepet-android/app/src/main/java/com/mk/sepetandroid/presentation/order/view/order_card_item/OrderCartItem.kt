package com.mk.sepetandroid.presentation.order.view.order_card_item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mk.sepetandroid.common.Constants
import com.mk.sepetandroid.domain.model.order.OrderModel
import com.mk.sepetandroid.domain.model.order.OrderStatTypes
import com.mk.sepetandroid.domain.model.order.checkOrderStatus
import com.mk.sepetandroid.domain.model.order.getOrderStatus
import com.mk.sepetandroid.presentation.order.OrderEvent
import com.mk.sepetandroid.presentation.order.OrderState
import com.mk.sepetandroid.presentation.widget.SepetText
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun OrderCartItem(
    order : OrderModel,
    state : OrderState,
    onEvent: (OrderEvent) -> Unit
){
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                val currentDetailId = state.orderDetail.firstOrNull()?.id


                if (currentDetailId == order.id) {
                    onEvent(OrderEvent.ClearDetail)
                } else {
                    onEvent(OrderEvent.ClearDetail)
                    onEvent(OrderEvent.GetOrderDetail(order))

                    onEvent(
                        OrderEvent.SelectedOrder(order)
                    )
                }
            }
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 5.dp,
                    bottom = 5.dp,
                    start = 3.dp,
                    end = 3.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column{

                SepetText(
                    "Order Date",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                SepetText(
                    dateFormat.format(order.orderDate)
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ){
                SepetText(
                    "Status",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                SepetText(
                    getOrderStatus(order.status)
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 5.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                Icons.Default.Info,
                contentDescription = "",
                modifier = Modifier
                    .size(13.dp)
                    .padding(
                        end = 3.dp
                    )
            )

            SepetText(
                text = "You can click for more information",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }


        AnimatedVisibility(
            visible = state.orderDetail.isNotEmpty() && state.orderDetail[0].id == order.id,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    5.dp
                )
        ){
            Column {

                if(state.orderDetail.isNotEmpty()){
                    val orderDetails = state.orderDetail

                    val orderAddress = orderDetails.first().address

                    SepetText(
                        "Order address",
                        fontWeight = FontWeight.Bold
                    )
                    SepetText(
                        text = "${orderAddress.fullAddressText.toString()} ${orderAddress.town}",
                    )

                    Row{
                        TextButton(
                            content = {
                                SepetText(
                                    text = "Change address"
                                )
                            },
                            onClick = {
                                onEvent(
                                    OrderEvent.ShowAddressDialog(true)
                                )
                            },
                            enabled = checkOrderStatus(order.status) == OrderStatTypes.ORDER_ORDERED
                        )

                        TextButton(
                            content = {
                                SepetText(
                                    text = "Cancel order",
                                    color = if(checkOrderStatus(order.status) == OrderStatTypes.ORDER_ORDERED) MaterialTheme.colorScheme.error else Color.Unspecified
                                )
                            },
                            onClick = {
                                onEvent(
                                    OrderEvent.ShowCancelDialog(true)
                                )
                            },
                            enabled = checkOrderStatus(order.status) == OrderStatTypes.ORDER_ORDERED
                        )
                    }
                    HorizontalDivider()

                    orderDetails.first().products.forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 10.dp

                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(Constants.IMAGE_STORAGE + it.product.image.first())
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(40.dp)
                            )

                            SepetText(
                                text = it.product.name ?: ""
                            )

                            SepetText(
                                text = it.product.price.toString() + "$"
                            )
                        }

                    }
                }

            }
        }
    }
}