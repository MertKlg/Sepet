package com.mk.sepetandroid.data.remote

import com.mk.sepetandroid.data.remote.dto.product.ProductsDto
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {
    @GET("product/all")
    suspend fun getProducts(
        @Query("min") min : Int?= null,
        @Query("max") max : Int?= null,
        @Query("minPrice") minPrice : Double?= null,
        @Query("maxPrice") maxPrice : Double?= null,
        @Query("category") category : String = ""
    ) : ResponseDto<List<ProductsDto>>
}