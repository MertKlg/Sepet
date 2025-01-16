package com.mk.sepetandroid.presentation.cart.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mk.sepetandroid.common.Constants
import com.mk.sepetandroid.domain.model.cart.CartModel
import com.mk.sepetandroid.presentation.cart.CartEvent
import com.mk.sepetandroid.presentation.widget.SepetButton
import com.mk.sepetandroid.presentation.widget.SepetText

@Composable
fun CartProducts(
    cart : List<CartModel>,
    onEvent: (CartEvent) -> Unit,
    takeOrder : () -> Unit
){
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp)
        ){
            items(cart) {
                it.products.forEach { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                    ){
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(Constants.IMAGE_STORAGE + product.product?.image?.first())
                                    .build(),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(130.dp)
                                    .padding(20.dp),
                                contentScale = ContentScale.Fit
                            )

                            Column(
                                horizontalAlignment = Alignment.Start,
                            ){

                                SepetText(
                                    text = product.product?.name ?: "",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                SepetText(
                                    text = product.product?.description ?: "",
                                    fontSize = 15.sp
                                )

                                SepetText(
                                    text = "${product.product?.price.toString()}$"
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    IconButton(
                                        content = {
                                            Icon(
                                                Icons.Default.KeyboardArrowUp,
                                                contentDescription = "Add",
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        },
                                        onClick = {
                                            onEvent(CartEvent.UpdateCartProduct(product.copy(count = (product.count ?: 1) + 1)))
                                        }
                                    )

                                    SepetText(
                                        text = product.count.toString()
                                    )

                                    IconButton(
                                        content = {
                                            Icon(
                                                Icons.Default.KeyboardArrowDown,
                                                contentDescription = "Add",
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        },
                                        onClick = {
                                            onEvent(CartEvent.UpdateCartProduct(product.copy(count = (product.count ?: 1) - 1)))
                                        }
                                    )
                                }

                                IconButton(
                                    content = {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "DELETE",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },

                                    onClick = {
                                        onEvent(CartEvent.UpdateCartProduct(product.copy(count = 0)))
                                    }
                                )
                            }
                        }
                    }

                }
            }
        }

        val total = if (cart.isNotEmpty()) {
            cart.first().total ?: 0.0
        } else {
            0.0
        }

        SepetButton(
            text = "Order $total",
            onClick = {
                takeOrder()
            },
            shape = RoundedCornerShape(topStartPercent = 15, topEndPercent = 15),
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            enabled = cart.isNotEmpty()
        )
    }
}