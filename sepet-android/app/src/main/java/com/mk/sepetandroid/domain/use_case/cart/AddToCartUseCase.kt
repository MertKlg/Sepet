package com.mk.sepetandroid.domain.use_case.cart

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toDto
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.product.toDto
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.response.AddProductModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.repository.ICartRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val iCartRepo: ICartRepo
) {
    fun invoke(accessToken : String, addProductModel: AddProductModel) = flow<Resource<ResponseModel<List<Any>>>>{
        val res = iCartRepo.addToCart(accessToken, addProductModel.toDto()).toModel()

        emit(Resource.Success(res))
    }.catchHandler {
        emit(Resource.Error(it.message, it.status))
    }
}