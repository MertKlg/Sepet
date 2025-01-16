package com.mk.sepetandroid.domain.repository

import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.order.OrderDto
import com.mk.sepetandroid.data.remote.dto.order.TakeOrderDto
import com.mk.sepetandroid.data.remote.dto.order.UpdateOrderDto

interface IOrderRepo {

    suspend fun getOrders(token : String): ResponseDto<List<OrderDto>>

    suspend fun getOrderDetail(token : String, id : String) : ResponseDto<List<OrderDto>>

    suspend fun takeOrder(token : String, cart : String, address : TakeOrderDto) : ResponseDto<List<Any>>

    suspend fun updateOrder(token : String, order : String, updateOrder : UpdateOrderDto) : ResponseDto<List<Any>>

}