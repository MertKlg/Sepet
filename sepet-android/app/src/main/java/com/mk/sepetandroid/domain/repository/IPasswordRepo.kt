package com.mk.sepetandroid.domain.repository

import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.password.UpdatePasswordDto

interface IPasswordRepo {
    suspend fun rePassword(accessToken : String, updatePasswordDto: UpdatePasswordDto) : ResponseDto<List<Any>>
}