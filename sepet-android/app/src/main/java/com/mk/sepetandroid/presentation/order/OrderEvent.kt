package com.mk.sepetandroid.presentation.order

import com.mk.sepetandroid.domain.model.order.OrderModel
import com.mk.sepetandroid.domain.model.order.UpdateOrderModel

sealed class OrderEvent {

    data object init : OrderEvent()
    data object ClearDetail : OrderEvent()
    data object CloseDialog : OrderEvent()

    data class GetOrderDetail(val orderModel: OrderModel) : OrderEvent()
    data class UpdateOrder(val orderModel: OrderModel?, val updateOrder : UpdateOrderModel) : OrderEvent()
    data class ShowAddressDialog(val showAddressDialog : Boolean) : OrderEvent()
    data class SelectedOrder(val orderModel: OrderModel? = null) : OrderEvent()
    data class ShowCancelDialog(val status : Boolean) : OrderEvent()
}