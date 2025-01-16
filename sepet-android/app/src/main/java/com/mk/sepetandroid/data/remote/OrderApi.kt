package com.mk.sepetandroid.data.remote

import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.order.OrderDto
import com.mk.sepetandroid.data.remote.dto.order.TakeOrderDto
import com.mk.sepetandroid.data.remote.dto.order.UpdateOrderDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderApi {
    @GET("order/all")
    suspend fun getOrders(@Header("Authorization") token: String) : ResponseDto<List<OrderDto>>

    @GET("order/detail/{id}")
    suspend fun getOrderDetail(@Header("Authorization") token: String, @Path("id") id: String) : ResponseDto<List<OrderDto>>

    @POST("order/{cart}")
    suspend fun takeOrder(@Header("Authorization") token: String,
                          @Path("cart") cart : String,
                          @Body address : TakeOrderDto
    ) : ResponseDto<List<Any>>

    @PUT("order/update/{order}")
    suspend fun updateOrder(
        @Header("Authorization") token: String,
        @Path("order") order : String,
        @Body updateOrderDto: UpdateOrderDto
    ) : ResponseDto<List<Any>>
}