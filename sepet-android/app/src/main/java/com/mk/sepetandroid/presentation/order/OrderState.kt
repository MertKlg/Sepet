package com.mk.sepetandroid.presentation.order

import com.mk.sepetandroid.domain.model.order.OrderModel
import com.mk.sepetandroid.domain.model.address.AddressModel

data class OrderState(
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val message : String = "",
    val orders : List<OrderModel> = emptyList(),
    val orderDetail : List<OrderModel> = emptyList(),
    val userAddress : List<AddressModel> = emptyList(),

    val showAddressDialog : Boolean = false,
    val selectedOrder : OrderModel? = null,

    val cancelDialog : Boolean = false
)