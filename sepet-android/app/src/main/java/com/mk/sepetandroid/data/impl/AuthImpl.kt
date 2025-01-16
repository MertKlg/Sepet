package com.mk.sepetandroid.data.impl

import com.mk.sepetandroid.data.remote.AuthApi
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.auth.TokenDto
import com.mk.sepetandroid.data.remote.dto.auth.SignInDto
import com.mk.sepetandroid.data.remote.dto.auth.RegisterDto
import com.mk.sepetandroid.domain.repository.IAuthRepo
import javax.inject.Inject

class AuthImpl @Inject constructor(
    private val authApi: AuthApi
) : IAuthRepo {

    override suspend fun login(signInDto: SignInDto): ResponseDto<List<TokenDto>> {
        return authApi.login(signInDto)
    }

    override suspend fun register(signUpDto: RegisterDto): ResponseDto<List<Any>> {
        return authApi.register(signUpDto)
    }

    override suspend fun refresh(refreshToken: String): ResponseDto<List<TokenDto>> {
        return authApi.refresh(refreshToken)
    }

}