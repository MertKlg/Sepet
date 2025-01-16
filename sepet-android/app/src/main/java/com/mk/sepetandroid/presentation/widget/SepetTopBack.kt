package com.mk.sepetandroid.presentation.widget

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mk.sepetandroid.R

@Composable
fun SepetTopBack(
    modifier: Modifier = Modifier,
    shape : Shape = RoundedCornerShape(100.dp),
    contentPadding : PaddingValues  = PaddingValues(0.dp),
    icon : Painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
    contentDescription : String = "Back to preview",
    onClick : () -> Unit
) {
    Button(
        onClick = onClick,
        shape = shape,
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription
        )
    }
}