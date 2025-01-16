package com.mk.sepetandroid.data.impl

import com.mk.sepetandroid.data.remote.OrderApi
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.order.OrderDto
import com.mk.sepetandroid.data.remote.dto.order.TakeOrderDto
import com.mk.sepetandroid.data.remote.dto.order.UpdateOrderDto
import com.mk.sepetandroid.domain.repository.IOrderRepo
import javax.inject.Inject

class OrderImpl @Inject constructor(private val api: OrderApi) : IOrderRepo {

    override suspend fun getOrders(token : String): ResponseDto<List<OrderDto>> {
        return api.getOrders(token)
    }

    override suspend fun getOrderDetail(token: String, id: String): ResponseDto<List<OrderDto>> {
        return api.getOrderDetail(token,id)
    }

    override suspend fun takeOrder(
        token: String,
        cart: String,
        address: TakeOrderDto
    ): ResponseDto<List<Any>> {
        return api.takeOrder(token,cart,address)
    }

    override suspend fun updateOrder(
        token: String,
        order: String,
        updateOrder: UpdateOrderDto
    ): ResponseDto<List<Any>> {
        return api.updateOrder(token,order,updateOrder)
    }
}