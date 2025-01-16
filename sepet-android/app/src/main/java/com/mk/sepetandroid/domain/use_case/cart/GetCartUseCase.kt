package com.mk.sepetandroid.domain.use_case.cart

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.cart.toModel
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.cart.CartModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.repository.ICartRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val iCartRepo: ICartRepo
) {
    fun invoke(accessToken : String) = flow<Resource<ResponseModel<List<CartModel>>>>{
        val res = iCartRepo.getCart(accessToken).toModel { it.map { it.toModel() } }
        emit(Resource.Success(res))
    }.catchHandler {
        emit(Resource.Error(it.message))
    }
}