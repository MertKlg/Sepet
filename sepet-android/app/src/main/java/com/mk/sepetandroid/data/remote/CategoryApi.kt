package com.mk.sepetandroid.data.remote


import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.product.CategoryDto
import retrofit2.http.GET
import retrofit2.http.Query


interface CategoryApi {
    @GET("category/")
    suspend fun getCategories(@Query("id") id : String ?= null) : ResponseDto<List<CategoryDto>>
}