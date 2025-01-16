package com.mk.sepetandroid.presentation.products

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.mk.sepetandroid.domain.model.response.CategoryModel
import com.mk.sepetandroid.domain.model.response.MinMax
import com.mk.sepetandroid.domain.model.response.ProductModel
import com.mk.sepetandroid.domain.model.response.ProductsModel
import com.mk.sepetandroid.domain.model.state.GenericState
import com.mk.sepetandroid.domain.model.view.DialogModel
import java.util.function.DoubleUnaryOperator

data class ProductsState(

    //val products : ProductsModel ?= null,
    val products : MutableList<ProductModel> = mutableListOf(),
    val minMax  : List<MinMax> = emptyList(),


    val onStatus : Boolean = false,
    val message : String = "",
    val isLoading : Boolean = false,
    val isEndOfList : Boolean = false,

    val min : Int = 0,
    val max : Int = 10,

    val minPrice: String = "",
    val maxPrice: String = "",

    val categories : List<CategoryModel> = emptyList(),
    val filterCategories : MutableList<CategoryModel> = mutableListOf(),

    val showFilters : Boolean = false
)