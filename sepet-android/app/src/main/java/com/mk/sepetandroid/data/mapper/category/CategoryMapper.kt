package com.mk.sepetandroid.data.mapper.category

import com.mk.sepetandroid.data.remote.dto.product.CategoryDto
import com.mk.sepetandroid.domain.model.response.CategoryModel

fun CategoryDto.toModel() = CategoryModel(this.id,this.name,this.image)
fun CategoryModel.toDto() = CategoryDto(this.id,this.name,this.image)