package com.mk.sepetandroid.domain.model.auth

data class TokenModel(
    val id : Int?,
    val accessToken : String?,
    val refreshToken : String?
){
    fun validAccessToken() = accessToken.isNullOrEmpty()
    fun validRefreshToken() = refreshToken.isNullOrEmpty()
}
