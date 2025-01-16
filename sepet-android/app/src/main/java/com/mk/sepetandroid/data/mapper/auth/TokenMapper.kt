package com.mk.sepetandroid.data.mapper.auth

import com.mk.sepetandroid.data.local.entity.TokenEntity
import com.mk.sepetandroid.data.remote.dto.auth.TokenDto
import com.mk.sepetandroid.domain.model.auth.TokenModel

fun TokenDto.toModel() = TokenModel(0,this.accessToken,this.refreshToken)

fun TokenEntity.toModel() = TokenModel(this.id,this.accessToken,this.refreshToken)

fun TokenModel.toEntity() = TokenEntity(this.id ?: 0, this.accessToken ?: "",
    this.refreshToken ?: ""
)
