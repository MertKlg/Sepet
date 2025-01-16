package com.mk.sepetandroid.domain.use_case.order

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.order.toModel
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.order.OrderModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.repository.IOrderRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderDetailUseCase @Inject constructor(
    private val iOrderRepo: IOrderRepo
){
    fun invoke(token : String, id : String) = flow<Resource<ResponseModel<List<OrderModel>>>>{
        val getDetail = iOrderRepo.getOrderDetail(token, id).toModel { it.map { it.toModel() } }
        emit(Resource.Success(getDetail))
    }.catchHandler {
        emit(Resource.Error(it.message, it.status))
    }
}