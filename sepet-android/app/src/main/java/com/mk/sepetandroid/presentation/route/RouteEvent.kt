package com.mk.sepetandroid.presentation.route

import com.mk.sepetandroid.domain.model.enums.RouterEnum

sealed class RouteEvent {
    data class Navigate(val route : RouterEnum) : RouteEvent()
}