package com.mk.sepetandroid.common

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import okhttp3.internal.format
import java.text.NumberFormat
import java.util.Locale


fun formatPrice(price : Double, locale: Locale = Locale.getDefault()): String? {
    return try{
        val formatter = NumberFormat.getCurrencyInstance(locale)
        formatter.format(price)
    }catch (e:Exception){
        e.printStackTrace()
        ""
    }
}