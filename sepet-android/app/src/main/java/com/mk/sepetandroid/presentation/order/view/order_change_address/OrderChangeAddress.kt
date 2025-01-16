package com.mk.sepetandroid.presentation.order.view.order_change_address

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.sepetandroid.domain.model.order.UpdateOrderModel
import com.mk.sepetandroid.domain.model.address.AddressModel
import com.mk.sepetandroid.presentation.order.OrderEvent
import com.mk.sepetandroid.presentation.order.OrderState
import com.mk.sepetandroid.presentation.widget.SepetButton
import com.mk.sepetandroid.presentation.widget.SepetText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderChangeAddress(
    state : OrderState,
    onEvent: (OrderEvent) -> Unit
){
    var selectedAddress by remember { mutableStateOf<AddressModel?>(null) }
    AnimatedVisibility(state.showAddressDialog) {
        BasicAlertDialog(
            onDismissRequest = {
                onEvent(
                    OrderEvent.ShowAddressDialog(false)
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.9F)
                    .fillMaxHeight(.6F)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(15.dp)
            ) {

                SepetText(
                    text = "Change address",
                    fontWeight = FontWeight.Bold
                )

                SepetText(
                    text = "Select an address",
                    fontSize = 13.sp
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.8F)
                ) {
                    items(state.userAddress){
                        Column(
                            modifier = Modifier
                                .padding(
                                    top = 10.dp
                                )
                                .border(
                                    width = .6.dp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(
                                    if(selectedAddress == it) MaterialTheme.colorScheme.onSurfaceVariant.copy(.4F)  else Color.Unspecified,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable {
                                    selectedAddress = if(selectedAddress != it) it else null
                                }
                                .fillMaxWidth()
                                .fillMaxWidth(.1F)
                                .padding(
                                    start = 10.dp,
                                    end = 10.dp
                                )
                        ) {
                            SepetText(
                                text = it.name ?: "",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp
                            )

                            SepetText(
                                it.fullAddressText.toString(),
                                fontSize = 15.sp
                            )

                        }
                    }
                }

                SepetButton(
                    text = "Change address",
                    onClick = {
                        onEvent(
                            OrderEvent.UpdateOrder(state.selectedOrder, UpdateOrderModel(
                            selectedAddress?.id,
                        )
                            ))

                        onEvent(
                            OrderEvent.SelectedOrder(null)
                        )

                        onEvent(
                            OrderEvent.ShowAddressDialog(false)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )


            }
        }
    }
}