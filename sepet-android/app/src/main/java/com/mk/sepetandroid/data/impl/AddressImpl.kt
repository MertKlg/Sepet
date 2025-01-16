package com.mk.sepetandroid.data.impl

import com.mk.sepetandroid.data.remote.AddressApi
import com.mk.sepetandroid.data.remote.dto.address.AddressDto
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.domain.repository.IAddressRepo
import javax.inject.Inject

class AddressImpl @Inject constructor(
    private val addressApi: AddressApi
) : IAddressRepo{
    override suspend fun getAddress(accessToken : String): ResponseDto<List<AddressDto>> {
        return addressApi.getAddress(accessToken)
    }

    override suspend fun updateAddress(accessToken : String,addressDto: AddressDto) : ResponseDto<List<Any>>{
        return addressApi.updateAddress(accessToken,addressDto)
    }

    override suspend fun deleteAddress(accessToken : String, id : String): ResponseDto<List<Any>> {
        return addressApi.deleteAddress(accessToken, id)
    }

    override suspend fun addAddress(accessToken : String,addressDto: AddressDto): ResponseDto<List<Any>> {
        return addressApi.addAddress(accessToken,addressDto)
    }

}