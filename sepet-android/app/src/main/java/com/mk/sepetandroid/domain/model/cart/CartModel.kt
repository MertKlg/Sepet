package com.mk.sepetandroid.domain.model.cart

import com.mk.sepetandroid.domain.model.response.ProductModel

data class CartModel(
    val id : String?,
    val products : MutableList<CartProductsModel> = mutableListOf(),
    val total : Double?,
)

data class CartProductsModel(
    val product : ProductModel?,
    val count : Int?
)