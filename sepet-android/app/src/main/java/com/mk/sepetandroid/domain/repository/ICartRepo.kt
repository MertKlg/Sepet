package com.mk.sepetandroid.domain.repository

import com.mk.sepetandroid.data.remote.dto.product.AddProductDto
import com.mk.sepetandroid.data.remote.dto.cart.CartDto
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.cart.UpdateCartDto

interface ICartRepo {
    suspend fun getCart(accessToken : String) : ResponseDto<List<CartDto>>
    suspend fun addToCart(accessToken: String,  addProductDto: AddProductDto) : ResponseDto<List<Any>>

    suspend fun updateCart(accessToken: String, cartId : String, updateCartDto: UpdateCartDto) : ResponseDto<List<Any>>

    suspend fun deleteProductCart(accessToken: String, id : String)
    suspend fun order(accessToken: String)
}