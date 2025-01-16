package com.mk.sepetandroid.data.remote

import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.auth.TokenDto
import com.mk.sepetandroid.data.remote.dto.auth.SignInDto
import com.mk.sepetandroid.data.remote.dto.auth.RegisterDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body signInDto: SignInDto) : ResponseDto<List<TokenDto>>


    @POST("auth/register")
    @Headers("Content-Type: application/json")
    suspend fun register(@Body registerDto: RegisterDto) : ResponseDto<List<Any>>


    @POST("auth/refresh")
    @Headers("Content-Type: application/json")
    suspend fun refresh(@Header("Authorization") refreshToken : String) : ResponseDto<List<TokenDto>>
}