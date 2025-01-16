package com.mk.sepetandroid.data.mapper.address

import com.mk.sepetandroid.data.remote.dto.address.AddressDto
import com.mk.sepetandroid.domain.model.address.AddressModel

fun AddressModel.toDto() = AddressDto(id, name, town, neighbourhood, fullAddressText)
fun AddressDto.toModel() = AddressModel(id, name, town, neighbourhood, fullAddressText)