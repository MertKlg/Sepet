package com.mk.sepetandroid.data.impl

import com.mk.sepetandroid.data.remote.ProductApi
import com.mk.sepetandroid.data.remote.dto.product.ProductsDto
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.domain.repository.IProductRepo
import javax.inject.Inject

class ProductImpl @Inject constructor(
    private val productApi: ProductApi,
) : IProductRepo {

    override suspend fun getProducts(
        min: Int?,
        max: Int?,
        minPrice: Double?,
        maxPrice : Double?,
        category: String): ResponseDto<List<ProductsDto>> {
        return productApi.getProducts(
            min, max,minPrice,maxPrice, category
        )
    }
}