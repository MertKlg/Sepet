package com.mk.sepetandroid.data.mapper.product

import com.mk.sepetandroid.data.remote.dto.product.AddProductDto
import com.mk.sepetandroid.data.remote.dto.product.MinMaxDto
import com.mk.sepetandroid.data.remote.dto.product.ProductDto
import com.mk.sepetandroid.data.remote.dto.product.ProductsDto
import com.mk.sepetandroid.domain.model.response.AddProductModel
import com.mk.sepetandroid.domain.model.response.MinMax
import com.mk.sepetandroid.domain.model.response.ProductModel
import com.mk.sepetandroid.domain.model.response.ProductsModel

fun ProductDto.toProductModel() = ProductModel(
    id,name, description, price, image, discountPrice,
)

fun ProductModel.toDto() = ProductDto(
    id, name, description, price, image, discountPrice
)


fun MinMaxDto.toModel() = MinMax(
    this.min,
    this.max,
    this.value
)

fun ProductsDto.toModel() = ProductsModel(
    products = this.products.map { it.toProductModel() }.toMutableList(),
    minMax = this.minMax.map { it.toModel()  }
)

fun AddProductDto.toModel() = AddProductModel(this.product, this.count)
fun AddProductModel.toDto() = AddProductDto(this.product, this.count)