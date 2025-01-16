package com.mk.sepetandroid.data.remote

import com.mk.sepetandroid.data.remote.dto.profile.ProfileDto
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface ProfileApi {
    @GET("profile/all")
    suspend fun getProfile(@Header("Authorization") token: String) : ResponseDto<List<ProfileDto>>

    @PUT("profile/update")
    suspend fun putProfile(@Header("Authorization") token: String, @Body profileDto: ProfileDto) : ResponseDto<List<Any>>
}