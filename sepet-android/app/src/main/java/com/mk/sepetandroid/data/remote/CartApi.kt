package com.mk.sepetandroid.data.remote

import com.mk.sepetandroid.data.remote.dto.product.AddProductDto
import com.mk.sepetandroid.data.remote.dto.cart.CartDto
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.cart.UpdateCartDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CartApi {
    @POST("cart/add")
    suspend fun addToCart(
        @Header("Authorization") accessToken : String,
        @Body addProductDto: AddProductDto
    ) : ResponseDto<List<Any>>

    @GET("cart/all")
    suspend fun getCart(@Header("Authorization") accessToken : String) : ResponseDto<List<CartDto>>

    @POST("cart/order")
    suspend fun order(@Header("Authorization") accessToken : String)

    @PUT("cart/update/{cartId}")
    suspend fun updateCart(@Header("Authorization") accessToken : String, @Path("cartId") cartId : String, @Body updateCartDto: UpdateCartDto) : ResponseDto<List<Any>>

    @DELETE("cart/delete/{id}")
    suspend fun deleteCart(@Header("Authorization") accessToken : String, @Path("id")  id : String)
}