package com.mk.sepetandroid.presentation.cart.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.sepetandroid.domain.model.cart.CartModel
import com.mk.sepetandroid.domain.model.address.AddressModel
import com.mk.sepetandroid.presentation.cart.CartEvent
import com.mk.sepetandroid.presentation.widget.SepetButton
import com.mk.sepetandroid.presentation.widget.SepetText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTakeOrder(
    cartModel : List<CartModel>,
    address : List<AddressModel>,
    onEvent : (CartEvent) -> Unit,
){
    var selectedAddress by remember { mutableStateOf<AddressModel?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        LazyColumn {
            items(address){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    enabled = selectedAddress != it,
                    onClick = {
                        selectedAddress = it
                    }
                ){

                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                    ){
                        SepetText(
                            it.name.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        SepetText(
                            it.fullAddressText.toString()
                        )
                    }

                }
            }
        }

        SepetButton(
            onClick = {
                onEvent(CartEvent.TakeOrder(cartModel.first(), selectedAddress))
            },
            text = "Take order",
            enabled = !(selectedAddress == null || cartModel.isEmpty()),
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),

            shape = RoundedCornerShape(0.dp)
        )
    }


}