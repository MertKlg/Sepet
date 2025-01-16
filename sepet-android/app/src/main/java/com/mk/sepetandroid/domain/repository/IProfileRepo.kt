package com.mk.sepetandroid.domain.repository

import com.mk.sepetandroid.data.remote.dto.profile.ProfileDto
import com.mk.sepetandroid.data.remote.dto.ResponseDto

interface IProfileRepo {
    suspend fun getProfile(token : String) : ResponseDto<List<ProfileDto>>

    suspend fun updateProfile(token : String, profileDto: ProfileDto) : ResponseDto<List<Any>>

}