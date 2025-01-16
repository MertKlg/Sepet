package com.mk.sepetandroid.domain.model.auth

data class SignUpModel (
    val name : String,
    val email : String,
    val password : String,
    val rePassword: String,
    val address : String,
    val phoneNumber : String
)