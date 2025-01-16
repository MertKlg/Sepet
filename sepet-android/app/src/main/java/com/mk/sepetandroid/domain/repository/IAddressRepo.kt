package com.mk.sepetandroid.domain.repository

import com.mk.sepetandroid.data.remote.dto.address.AddressDto
import com.mk.sepetandroid.data.remote.dto.ResponseDto


interface IAddressRepo {
    suspend fun getAddress(accessToken : String) : ResponseDto<List<AddressDto>>

    suspend fun updateAddress(accessToken : String,addressDto: AddressDto) : ResponseDto<List<Any>>

    suspend fun deleteAddress(accessToken : String, id : String) : ResponseDto<List<Any>>

    suspend fun addAddress(accessToken : String,addressDto: AddressDto) : ResponseDto<List<Any>>
}