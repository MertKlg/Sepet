package com.mk.sepetandroid.common

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mk.sepetandroid.domain.model.response.ResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import java.io.IOException


fun <T> Flow<T>.catchHandler(action: suspend FlowCollector<T>.(Resource.Error<String>) -> Unit): Flow<T> {
    return catch {
        when (it) {
            is retrofit2.HttpException -> {
                val errorResponse = parseHttpError(it)

                if (errorResponse != null) {
                    action(Resource.Error(message = errorResponse.message, status = errorResponse.status))
                } else {
                    action(Resource.Error(message = "An unknown error occurred.", 500))
                }
            }
            is IOException -> {

                action(Resource.Error(message = "Network error. Please check your connection.",500))
            }
            else -> {

                action(Resource.Error(message = it.localizedMessage ?: "An unexpected error occurred.",500))
            }
        }
    }
}


fun parseHttpError(exception: retrofit2.HttpException): ResponseModel<List<Any>>? {
    return try {
        val errorBody = exception.response()?.errorBody()?.string()
        if (!errorBody.isNullOrEmpty()) {
            val gson = Gson()
            val type = object : TypeToken<ResponseModel<List<Any>>>() {}.type
            gson.fromJson(errorBody, type)
        } else {
            null
        }
    } catch (e: Exception) {
        println(e.localizedMessage)
        null
    }
}