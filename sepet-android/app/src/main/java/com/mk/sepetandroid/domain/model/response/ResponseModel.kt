package com.mk.sepetandroid.domain.model.response

data class ResponseModel<T>(
    val message : String,
    val status : Int,
    val data : T
)
