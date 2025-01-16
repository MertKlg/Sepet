package com.mk.sepetandroid.data.remote.dto.product

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("_id")
    val id : String,
    val name : String,
    val image : String
)