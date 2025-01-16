package com.mk.sepetandroid.domain.use_case.product

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.order.toModel
import com.mk.sepetandroid.data.mapper.product.toModel
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.response.ProductsModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.repository.IProductRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepo : IProductRepo
) {
    fun invoke(
        min : Int?= null,
        max : Int?= null,
        minPrice : Double?= null,
        maxPrice : Double?= null,
        category : String = "",
    ) = flow<Resource<ResponseModel<List<ProductsModel>>>>{

        val response = productRepo.getProducts(min,max,minPrice,maxPrice,category)

        emit(Resource.Success(response.toModel {
            it.map { it.toModel() }
        }))
    }.catchHandler {
        emit(Resource.Error(it.message, it.status))
    }

}