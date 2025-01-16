package com.mk.sepetandroid.domain.model.auth

data class RegisterModel(
    val username : String,
    val email : String,
    val phone : String,
    val password : String,
    val passwordAgain : String,
)
