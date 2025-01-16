package com.mk.sepetandroid.presentation.password



data class PasswordState(
    val isSuccess: Boolean = false,
    val isError : Boolean = false,
    val message : String?= null
)