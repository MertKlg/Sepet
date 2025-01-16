package com.mk.sepetandroid.data.mapper.profile

import com.mk.sepetandroid.data.remote.dto.profile.ProfileDto
import com.mk.sepetandroid.domain.model.profile.ProfileModel

fun ProfileDto.toModel() = ProfileModel(username = this.username, email = this.email, image = this.image ,phone = this.phone)
fun ProfileModel.toDto() = ProfileDto(username = this.username, email = this.email,image = this.image, phone = this.phone)