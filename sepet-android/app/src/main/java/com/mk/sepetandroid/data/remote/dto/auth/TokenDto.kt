package com.mk.sepetandroid.data.remote.dto.auth

data class TokenDto(
    val accessToken : String?= null,
    val refreshToken : String?= null
)
