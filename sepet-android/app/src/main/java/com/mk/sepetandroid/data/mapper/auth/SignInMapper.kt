package com.mk.sepetandroid.data.mapper.auth

import com.mk.sepetandroid.data.remote.dto.auth.SignInDto
import com.mk.sepetandroid.domain.model.auth.SignInModel


fun SignInModel.toDto() = SignInDto(this.email, this.password)
fun SignInDto.toDto() = SignInModel(this.email, this.password)