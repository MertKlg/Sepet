package com.mk.sepetandroid.domain.model.order

import com.google.gson.annotations.SerializedName
import com.mk.sepetandroid.domain.model.address.AddressModel
import com.mk.sepetandroid.domain.model.response.ProductModel
import com.mk.sepetandroid.domain.model.profile.ProfileModel
import java.util.Date

data class OrderModel(
    @SerializedName("_id")
    val id : String,
    val products : List<OrderProductModel>,
    val address : AddressModel,
    val user : ProfileModel,
    val total : Double,
    val orderDate : Date,
    val status : String
)


data class OrderProductModel(
    val product : ProductModel,
    val count : Int
)