package com.mk.sepetandroid.presentation.order.view.order_top_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.mk.sepetandroid.R
import com.mk.sepetandroid.presentation.widget.SepetText
import com.mk.sepetandroid.presentation.widget.SepetTopBar

@Composable
fun OrderTopBar(
    navHostController: NavHostController
){
    SepetTopBar(
        title = { SepetText(text = "Orders") },
        navigationIcon = {
            IconButton(
                onClick = {
                    navHostController.navigateUp()
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                    contentDescription = ""
                )
            }
        }
    )
}