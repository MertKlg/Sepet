package com.mk.sepetandroid.common

import com.mk.sepetandroid.domain.model.response.ResponseModel

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String?= null, val status : Int? = null) : Resource<T>()
}