package com.mk.sepetandroid.presentation.products.view.products_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mk.sepetandroid.common.Constants
import com.mk.sepetandroid.common.formatPrice
import com.mk.sepetandroid.domain.model.response.ProductModel
import com.mk.sepetandroid.domain.model.response.ProductsModel
import com.mk.sepetandroid.presentation.products.ProductsEvent
import com.mk.sepetandroid.presentation.products.ProductsState
import com.mk.sepetandroid.presentation.widget.SepetText

@Composable
fun ProductsItem(
    onEvent : (ProductsEvent) -> Unit,
    state : ProductsState
){
    val height = LocalConfiguration.current.screenHeightDp
    val listState = rememberLazyStaggeredGridState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        LazyVerticalStaggeredGrid(
            userScrollEnabled = true,
            modifier = Modifier,
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 2.dp,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            state = listState
        ) {
            itemsIndexed(state.products) { index,it ->
                Card(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp)),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(Constants.IMAGE_STORAGE + it.image.first())
                                .build(),
                            contentDescription = "${it.name} product image",
                            modifier = Modifier
                                .height((height * .06).dp),
                            contentScale = ContentScale.Fit
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            SepetText(
                                text = formatPrice(it.price ?: 0.0) ?: "",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            IconButton(
                                onClick = {
                                    onEvent(ProductsEvent.AddToCart(it))
                                },
                                content = {
                                    Icon(
                                        Icons.Default.AddCircle,
                                        contentDescription = "Add product to your cart"
                                    )
                                }
                            )
                        }

                        SepetText(
                            text = it.name.toString(),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        SepetText(
                            text = it.description.toString(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

                if(!state.isEndOfList && index >= state.products.size - 1 && !state.isLoading){
                    println("isEndOfList : ${state.isEndOfList}, minMax : ${state.min},${state.max}, price min and max : ${state.minPrice} - ${state.maxPrice}")
                    onEvent(
                        ProductsEvent.GetProducts(
                            min = state.min + (state.max - state.min),
                            max = state.max + 10,
                            minPrice = state.minPrice,
                            maxPrice = state.maxPrice,
                            category = state.filterCategories
                        )
                    )
                }
            }
        }
    }
}