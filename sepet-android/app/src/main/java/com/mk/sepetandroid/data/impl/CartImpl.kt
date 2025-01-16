package com.mk.sepetandroid.data.impl

import com.mk.sepetandroid.data.remote.CartApi
import com.mk.sepetandroid.data.remote.dto.product.AddProductDto
import com.mk.sepetandroid.data.remote.dto.cart.CartDto
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.cart.UpdateCartDto
import com.mk.sepetandroid.domain.repository.ICartRepo
import javax.inject.Inject

class CartImpl @Inject constructor(
    private val cartApi: CartApi
) : ICartRepo {

    override suspend fun getCart(accessToken: String) : ResponseDto<List<CartDto>> {
        return cartApi.getCart(accessToken)
    }

    override suspend fun addToCart(
        accessToken: String,
        addProductDto: AddProductDto
    ): ResponseDto<List<Any>> {
        return cartApi.addToCart(
            accessToken,
            addProductDto
        )
    }


    override suspend fun updateCart(accessToken: String, cartId : String, updateCartDto: UpdateCartDto) : ResponseDto<List<Any>> {
        return cartApi.updateCart(accessToken,cartId,updateCartDto)
    }

    override suspend fun deleteProductCart(accessToken: String, id : String) {
        cartApi.deleteCart(accessToken, id)
    }

    override suspend fun order(accessToken: String) {
        cartApi.order(accessToken)
    }

}