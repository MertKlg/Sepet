package com.mk.sepetandroid.data.remote.dto.order

import com.mk.sepetandroid.domain.model.order.OrderStatTypes

data class UpdateOrderDto(
    val address : String?= null,
    val orderStatus : String ?= null
)