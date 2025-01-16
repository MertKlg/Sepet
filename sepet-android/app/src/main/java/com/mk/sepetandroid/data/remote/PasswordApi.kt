package com.mk.sepetandroid.data.remote

import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.password.UpdatePasswordDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PUT

interface PasswordApi {
    @PUT("auth/re-password")
    @Headers("Content-Type: application/json")
    suspend fun rePassword(@Header("Authorization") accessToken : String, @Body updatePasswordDto: UpdatePasswordDto) : ResponseDto<List<Any>>
}