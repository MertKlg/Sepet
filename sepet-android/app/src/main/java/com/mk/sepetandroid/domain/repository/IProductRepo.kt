package com.mk.sepetandroid.domain.repository

import com.mk.sepetandroid.data.remote.dto.product.ProductsDto
import com.mk.sepetandroid.data.remote.dto.ResponseDto

interface IProductRepo {
    suspend fun getProducts(
        min : Int?= null,
        max : Int?= null,
        minPrice : Double?= null,
        maxPrice : Double?= null,
        category : String = "",
    ) : ResponseDto<List<ProductsDto>>
}