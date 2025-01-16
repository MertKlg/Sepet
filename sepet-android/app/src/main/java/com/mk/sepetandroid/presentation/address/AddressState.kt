package com.mk.sepetandroid.presentation.address

import com.mk.sepetandroid.domain.model.address.AddressModel

data class AddressState(
    val onStatus : Boolean = false,
    val message: String ?= null,
    val addressList : List<AddressModel> = emptyList(),
    val willUpdateAddress : AddressModel?= null
)
