package com.mk.sepetandroid.data.mapper.auth

import com.mk.sepetandroid.data.remote.dto.auth.RegisterDto
import com.mk.sepetandroid.domain.model.auth.RegisterModel

fun RegisterDto.toModel() = RegisterModel(username, email, phone, password, passwordAgain)
fun RegisterModel.toDto() = RegisterDto(username, email, phone, password, passwordAgain)