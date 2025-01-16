package com.mk.sepetandroid.data.remote.dto.order

import com.google.gson.annotations.SerializedName
import com.mk.sepetandroid.data.remote.dto.address.AddressDto
import com.mk.sepetandroid.data.remote.dto.product.ProductDto
import com.mk.sepetandroid.data.remote.dto.profile.ProfileDto
import java.util.Date

data class OrderDto(
    @SerializedName("_id")
    val id : String,
    val products : List<OrderProductDto>,
    val address : AddressDto,
    val user : ProfileDto,
    val total : Double,
    val orderDate : Date,
    val status : String
)


data class OrderProductDto(
    val product : ProductDto,
    val count : Int
)