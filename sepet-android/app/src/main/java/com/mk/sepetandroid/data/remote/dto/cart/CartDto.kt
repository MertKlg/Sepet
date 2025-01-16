package com.mk.sepetandroid.data.remote.dto.cart

import com.google.gson.annotations.SerializedName
import com.mk.sepetandroid.data.remote.dto.product.ProductDto

data class CartDto(
    @SerializedName("_id")
    val id : String?,
    val products : MutableList<CartProductsDto> = mutableListOf(),
    val total : Double?,
)

data class CartProductsDto(
    val product : ProductDto?,
    val count : Int?
)
