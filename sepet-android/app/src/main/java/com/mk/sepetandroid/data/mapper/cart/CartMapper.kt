package com.mk.sepetandroid.data.mapper.cart

import com.mk.sepetandroid.data.mapper.order.toDto
import com.mk.sepetandroid.data.mapper.product.toDto
import com.mk.sepetandroid.data.mapper.product.toProductModel
import com.mk.sepetandroid.data.remote.dto.cart.CartDto
import com.mk.sepetandroid.data.remote.dto.cart.CartProductsDto
import com.mk.sepetandroid.data.remote.dto.cart.UpdateCartDto

import com.mk.sepetandroid.domain.model.cart.CartModel
import com.mk.sepetandroid.domain.model.cart.CartProductsModel
import com.mk.sepetandroid.domain.model.cart.UpdateCartModel

fun CartDto.toModel() = CartModel(id, products.map { it.toModel() }.toMutableList() ,total)
fun CartModel.toDto() = CartDto(id, products.map { it.toDto() }.toMutableList(),total)

fun CartProductsModel.toDto() = CartProductsDto(product?.toDto(),count)
fun CartProductsDto.toModel() = CartProductsModel(product?.toProductModel(),count)


fun UpdateCartDto.toModel() = UpdateCartModel(this.product, this.count)
fun UpdateCartModel.toDto() = UpdateCartDto(this.product, this.count)