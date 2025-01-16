package com.mk.sepetandroid.domain.repository

import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.auth.TokenDto
import com.mk.sepetandroid.data.remote.dto.auth.SignInDto
import com.mk.sepetandroid.data.remote.dto.auth.RegisterDto

interface IAuthRepo {
    suspend fun login(signInDto: SignInDto) : ResponseDto<List<TokenDto>>
    suspend fun register(signUpDto: RegisterDto) : ResponseDto<List<Any>>
    suspend fun refresh(refreshToken : String) : ResponseDto<List<TokenDto>>
}