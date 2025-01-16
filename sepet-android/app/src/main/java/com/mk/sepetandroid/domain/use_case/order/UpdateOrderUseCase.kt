package com.mk.sepetandroid.domain.use_case.order

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.order.toModel
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.order.UpdateOrderModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.repository.IOrderRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(
    private val iOrderRepo: IOrderRepo
) {
    fun invoke(token : String, order : String, updateOrder : UpdateOrderModel) = flow<Resource<ResponseModel<List<Any>>>>{
        val res = iOrderRepo.updateOrder(token,order,updateOrder.toModel())
        emit(Resource.Success(res.toModel()))
    }.catchHandler {
        emit(Resource.Error(it.message,it.status))
    }
}