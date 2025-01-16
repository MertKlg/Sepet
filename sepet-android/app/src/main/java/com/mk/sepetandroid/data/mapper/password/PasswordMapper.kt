package com.mk.sepetandroid.data.mapper.password

import com.mk.sepetandroid.data.remote.dto.password.UpdatePasswordDto
import com.mk.sepetandroid.domain.model.auth.UpdatePasswordModel

fun UpdatePasswordDto.toUpdatePasswordModel() = UpdatePasswordModel(password, newPassword)
fun UpdatePasswordModel.toUpdatePasswordDto() = UpdatePasswordDto(password, newPassword)