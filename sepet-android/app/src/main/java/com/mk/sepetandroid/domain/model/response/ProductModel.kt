package com.mk.sepetandroid.domain.model.response


data class ProductsModel(
    val products : MutableList<ProductModel> = mutableListOf(),
    val minMax  : List<MinMax> = emptyList()
)

data class ProductModel(
    val id : String? = "",
    val name : String? = "",
    val description : String? = "",
    val price : Double? = 0.0,
    val image : List<String> = emptyList(),
    val discountPrice : Float? = 0F,
    val discountStatus : Boolean = false,
)


data class MinMax (
    val min : Int,
    val max : Int,
    val value: String
)


data class AddProductModel(
    // Product mean is productId
    val product : String,
    val count : Int
)