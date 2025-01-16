package com.mk.sepetandroid.domain.model.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.mk.sepetandroid.domain.model.enums.RouterEnum

data class NavBarItem(
    val id : Int = 0,
    val icon : ImageVector = Icons.Default.Home,
    val label : String = "Home",
    val route : RouterEnum = RouterEnum.PRODUCTS_SCREEN,
    var badgeCount : Int = 0,
){
    fun getNavBarItems() : List<NavBarItem> = listOf(
        NavBarItem(
            id = 0,
            label = "Products",
            route = RouterEnum.PRODUCTS_SCREEN,
            badgeCount = 0,
        ),
        NavBarItem(
            id = 1,
            Icons.Default.ShoppingCart,
            "Cart",
            RouterEnum.CART_SCREEN,
            badgeCount
        ),
        NavBarItem(
            id = 2,
            Icons.Default.Person,
            "Profile",
            RouterEnum.PROFILE_SCREEN,
            badgeCount
        )
    )
}
