package com.mk.sepetandroid.domain.use_case.order

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toDto
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.order.toDto
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.order.TakeOrderModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.repository.IOrderRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TakeOrderUseCase @Inject constructor(
    private val iOrderRepo: IOrderRepo
) {
    fun invoke(accessToken : String, cart : String, address : String) = flow<Resource<ResponseModel<List<Any>>>> {

        val res = iOrderRepo.takeOrder(accessToken,cart, TakeOrderModel(address).toDto()).toModel()

        emit(Resource.Success(res))
    }.catchHandler {
        emit(Resource.Error(it.message,it.status))
    }
}