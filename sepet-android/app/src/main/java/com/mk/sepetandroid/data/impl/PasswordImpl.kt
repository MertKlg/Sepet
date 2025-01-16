package com.mk.sepetandroid.data.impl

import com.mk.sepetandroid.data.remote.PasswordApi
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.password.UpdatePasswordDto
import com.mk.sepetandroid.domain.repository.IAddressRepo
import com.mk.sepetandroid.domain.repository.IPasswordRepo
import javax.inject.Inject

class PasswordImpl @Inject constructor(
    private val passwordApi : PasswordApi
) : IPasswordRepo{
    override suspend fun rePassword(accessToken : String,updatePasswordDto: UpdatePasswordDto): ResponseDto<List<Any>> {
        return passwordApi.rePassword(accessToken,updatePasswordDto)
    }
}