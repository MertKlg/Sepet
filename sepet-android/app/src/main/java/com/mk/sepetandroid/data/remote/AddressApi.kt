package com.mk.sepetandroid.data.remote

import com.mk.sepetandroid.data.remote.dto.address.AddressDto
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddressApi {
    @GET("address/all")
    suspend fun getAddress(@Header("Authorization") accessToken : String) : ResponseDto<List<AddressDto>>

    @PUT("address/update")
    suspend fun updateAddress(@Header("Authorization") accessToken : String, @Body addressDto: AddressDto) : ResponseDto<List<Any>>

    @POST("address/add")
    suspend fun addAddress(@Header("Authorization") accessToken : String, @Body addressDto: AddressDto) : ResponseDto<List<Any>>

    @DELETE("address/{id}")
    suspend fun deleteAddress(@Header("Authorization") accessToken : String, @Path("id") id : String) : ResponseDto<List<Any>>
}