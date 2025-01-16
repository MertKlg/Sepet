package com.mk.sepetandroid.data.mapper.response

import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.data.remote.dto.order.OrderDto
import com.mk.sepetandroid.domain.model.response.ResponseModel

fun<T> ResponseDto<T>.toModel() = ResponseModel(this.message,this.status,this.data)

fun<T> ResponseModel<T>.toDto() = ResponseDto(this.message,this.status,this.data)

fun <T,R> ResponseDto<T>.toModel(transform: (T) -> R)  : ResponseModel<R>{
    return ResponseModel(
        data = transform(this.data),
        message = this.message,
        status = this.status
    )
}


