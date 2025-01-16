package com.mk.sepetandroid.domain.use_case.cart

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toDto
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.cart.toDto
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.cart.UpdateCartModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.repository.ICartRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateCartUseCase @Inject constructor(
    private val iCartRepo: ICartRepo
) {
    fun invoke(accessToken : String, cartId : String, updateCartModel: UpdateCartModel) = flow<Resource<ResponseModel<List<Any>>>>{

        val res = iCartRepo.updateCart(accessToken, cartId, updateCartModel.toDto())

        emit(Resource.Success(res.toModel()))
    }.catchHandler {
        emit(Resource.Error(it.message,it.status))
    }
}