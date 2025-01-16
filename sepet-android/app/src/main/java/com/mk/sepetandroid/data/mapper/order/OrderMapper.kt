package com.mk.sepetandroid.data.mapper.order

import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.profile.toModel
import com.mk.sepetandroid.data.mapper.product.toProductModel
import com.mk.sepetandroid.data.remote.dto.order.OrderDto
import com.mk.sepetandroid.data.remote.dto.order.OrderProductDto
import com.mk.sepetandroid.data.remote.dto.order.TakeOrderDto
import com.mk.sepetandroid.data.remote.dto.order.UpdateOrderDto
import com.mk.sepetandroid.domain.model.order.OrderModel
import com.mk.sepetandroid.domain.model.order.OrderProductModel
import com.mk.sepetandroid.domain.model.order.TakeOrderModel
import com.mk.sepetandroid.domain.model.order.UpdateOrderModel


fun OrderDto.toModel() = OrderModel(
    id,
    products.map { it.toModel() },
    address.toModel(),
    user.toModel(),
    total,
    orderDate,
    status
)

fun OrderProductDto.toModel() = OrderProductModel(
    product.toProductModel(),
    count
)

fun TakeOrderDto.toModel() = TakeOrderModel(this.address)
fun TakeOrderModel.toDto() = TakeOrderDto(this.address)

fun UpdateOrderDto.toModel() = UpdateOrderModel(this.address, this.orderStatus)
fun UpdateOrderModel.toModel() = UpdateOrderDto(this.address, this.orderStatus)