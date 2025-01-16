package com.mk.sepetandroid.data.remote.dto.address

import com.google.gson.annotations.SerializedName

data class AddressDto(
    @SerializedName("_id")
    val id : String?,
    val name : String?,
    val town : String?,
    val neighbourhood : String?,
    val fullAddressText : String?
)
