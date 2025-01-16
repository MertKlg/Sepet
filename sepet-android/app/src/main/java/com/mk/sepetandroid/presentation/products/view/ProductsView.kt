package com.mk.sepetandroid.presentation.products.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.mk.sepetandroid.presentation.products.ProductsEvent
import com.mk.sepetandroid.presentation.products.ProductsState
import com.mk.sepetandroid.presentation.products.view.products_item.ProductsItem
import com.mk.sepetandroid.presentation.widget.SepetButton
import com.mk.sepetandroid.presentation.widget.SepetText
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsView(
    onEvent : (ProductsEvent) -> Unit,
    state: ProductsState,
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ){ it ->
        it.calculateBottomPadding()

        when(state.products){
            null -> {
                SepetText(
                    text = "No products found"
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    SepetButton(
                        text = "Filters",
                        onClick = {
                            onEvent(ProductsEvent.ShowFilters(true))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        shape = RoundedCornerShape(0.dp)
                    )

                    ProductsItem(
                        onEvent,
                        state
                    )
                }


                AnimatedVisibility(state.showFilters) {
                    BasicAlertDialog(
                        onDismissRequest = {
                            onEvent(
                                ProductsEvent.ShowFilters(false)
                            )
                        }
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(.9F)
                                .fillMaxHeight(.6F)
                                .clip(RoundedCornerShape(24.dp))
                                .background(MaterialTheme.colorScheme.surfaceContainer)
                                .padding(15.dp)
                        ) {
                            SepetText(
                                text = "Filters",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            HorizontalDivider()

                            SepetText(
                                text = "Price"
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {

                                OutlinedTextField(
                                    modifier = Modifier
                                        .weight(1f),
                                    value = state.minPrice,
                                    onValueChange = { newTextFieldValue ->
                                        onEvent(
                                            ProductsEvent.ChangeMinPrice(newTextFieldValue.filter { it.isDigit() || it.toString() == "." || it.toString() == "," })
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    placeholder = {
                                        SepetText(
                                            text = "Min price"
                                        )
                                    }
                                )


                                Spacer(
                                    modifier = Modifier
                                        .width(8.dp)
                                )


                                OutlinedTextField(
                                    modifier = Modifier
                                        .weight(1f),
                                    value = state.maxPrice,
                                    onValueChange = { newTextFieldValue ->
                                        onEvent(
                                            ProductsEvent.ChangeMaxPrice(newTextFieldValue.filter { it.isDigit() || it.toString() == "." || it.toString() == "," })
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    placeholder = {
                                        SepetText(
                                            text = "Max price"
                                        )
                                    }
                                )
                            }

                            SepetText(
                                text = "Categories"
                            )

                            LazyColumn {
                                items(state.categories) { category ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = state.filterCategories.any { it.id == category.id },
                                            onCheckedChange = {
                                                onEvent(
                                                    ProductsEvent.FilterCategories(category)
                                                )
                                            }
                                        )

                                        SepetText(
                                            text = category.name
                                        )
                                    }
                                }
                            }

                            TextButton(
                                content = {
                                    SepetText(
                                        text = "Apply"
                                    )
                                },
                                onClick = {
                                    onEvent(
                                        ProductsEvent.ClearProducts
                                    )

                                    onEvent(
                                        ProductsEvent.GetProducts(
                                           0,
                                            10,
                                            state.minPrice,
                                            state.maxPrice,
                                            state.filterCategories
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                }


            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        onEvent(ProductsEvent.GetProducts(state.min,state.max,"","", emptyList()))
        onEvent(ProductsEvent.GetCategories)
    }

    LaunchedEffect(key1 = state.onStatus) {
        if(state.onStatus){
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                val res = snackbarHostState.showSnackbar(
                    state.message,
                    withDismissAction = true
                )

                when(res){
                    SnackbarResult.Dismissed -> {
                        onEvent(ProductsEvent.CloseDialog)
                    }

                    SnackbarResult.ActionPerformed -> {}
                }

            }
        }
    }
}
