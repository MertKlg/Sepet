package com.mk.sepetandroid.domain.model.order

enum class OrderStatTypes(val value: String) {
    ORDER_ORDERED("Ordered"),
    ORDER_SHIPPED("Shipped"),
    ORDER_DELIVERED("Delivered"),
    ORDER_CANCELED("Cancelled"),
    ORDER_UNKNOWN("Unknown")
}

fun getOrderStatus(value: String): String {
    return (OrderStatTypes.entries.find { it.name.trim().uppercase() == value.trim().uppercase() } ?: OrderStatTypes.ORDER_UNKNOWN).value
}

fun checkOrderStatus(value : String) : OrderStatTypes{
    return (OrderStatTypes.entries.find { it.name.trim().uppercase() == value.trim().uppercase() } ?: OrderStatTypes.ORDER_UNKNOWN)
}
