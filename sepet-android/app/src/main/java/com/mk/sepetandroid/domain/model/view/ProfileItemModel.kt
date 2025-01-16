package com.mk.sepetandroid.domain.model.view

import android.media.Image
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.mk.sepetandroid.R
import com.mk.sepetandroid.domain.model.enums.RouterEnum


data class ProfileItemModel(
    val name : String?= null,
    val route : RouterEnum?= null,
    val icon : Int ?= null,
){
    fun getProfileItems() : List<ProfileItemModel> = listOf(
        ProfileItemModel(
            "Orders",
            icon = R.drawable.baseline_shopping_bag_24,
            route = RouterEnum.ORDER_VIEW
        ),
        ProfileItemModel(
            "My Details",
            icon = R.drawable.baseline_person_24,
            route = RouterEnum.MY_DETAILS
        ),
        ProfileItemModel(
            "Delivery Address",
            icon = R.drawable.baseline_location_pin_24,
            route = RouterEnum.ADDRESS_SCREEN
        ),

        ProfileItemModel(
            "Change Password",
            icon = R.drawable.baseline_lock_outline_24,
            route = RouterEnum.REFRESH_PASSWORD
        ),
    )
}