package com.mk.sepetandroid.domain.use_case.cart

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.domain.repository.ICartRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderCartUseCase @Inject constructor(
    private val iCartRepo: ICartRepo
) {
    fun invoke(accessToken : String) = flow<Resource<String>>{
        emit(Resource.Loading())
        iCartRepo.order(accessToken)
        emit(Resource.Success("We're take your order"))
    }.catchHandler {
        emit(Resource.Error(it.message,))
    }
}