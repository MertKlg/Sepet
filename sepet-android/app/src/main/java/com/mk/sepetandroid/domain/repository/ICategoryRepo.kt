package com.mk.sepetandroid.domain.repository

import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.product.CategoryDto

interface ICategoryRepo {
    suspend fun getCategories(id : String?) : ResponseDto<List<CategoryDto>>
}