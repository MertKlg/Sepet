package com.mk.sepetandroid.data.remote.dto

data class ResponseDto<T>(
    val message : String,
    val status : Int,
    val data : T
)
