package com.mk.sepetandroid.data.impl

import com.mk.sepetandroid.data.remote.ProfileApi
import com.mk.sepetandroid.data.remote.dto.profile.ProfileDto
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.domain.repository.IProfileRepo
import javax.inject.Inject

class ProfileImpl @Inject constructor(
    private val profileApi : ProfileApi
) : IProfileRepo{

    override suspend fun getProfile(token: String): ResponseDto<List<ProfileDto>> {
        return profileApi.getProfile(token)
    }

    override suspend fun updateProfile(
        token: String,
        profileDto: ProfileDto
    ): ResponseDto<List<Any>> {
        return profileApi.putProfile(token,profileDto)
    }

}