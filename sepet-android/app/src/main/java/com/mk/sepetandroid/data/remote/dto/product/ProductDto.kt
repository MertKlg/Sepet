package com.mk.sepetandroid.data.remote.dto.product

import com.google.gson.annotations.SerializedName

data class ProductsDto(
    val products : List<ProductDto> = emptyList(),
    val minMax  : List<MinMaxDto> = emptyList()
)


data class ProductDto(
    @SerializedName("_id")
    val id : String? = "",
    val name : String? = "",
    val description : String? = "",
    val price : Double? = 0.0,
    val image : List<String> = emptyList(),
    val discountPrice : Float? = 0F,
)

data class MinMaxDto (
    val min : Int,
    val max : Int,
    val value: String
)


data class AddProductDto(
    // Product mean is productId
    val product : String,
    val count : Int
)