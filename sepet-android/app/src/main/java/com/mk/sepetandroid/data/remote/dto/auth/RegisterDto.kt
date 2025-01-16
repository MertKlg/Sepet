package com.mk.sepetandroid.data.remote.dto.auth

data class RegisterDto(
    val username : String,
    val email : String,
    val phone : String,
    val password : String,
    val passwordAgain : String,
)
