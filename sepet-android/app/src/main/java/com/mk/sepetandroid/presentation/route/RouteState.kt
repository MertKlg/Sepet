package com.mk.sepetandroid.presentation.route

import com.mk.sepetandroid.domain.model.enums.RouterEnum

data class RouteState(
    val navigation : String = RouterEnum.PRODUCTS_SCREEN.name
)