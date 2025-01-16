package com.mk.sepetandroid.domain.use_case.user

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.auth.toModel
import com.mk.sepetandroid.data.mapper.order.toModel
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.model.auth.TokenModel
import com.mk.sepetandroid.domain.repository.IAuthRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val iAuthRepo: IAuthRepo
) {
    fun invoke(refreshToken : String) = flow<Resource<ResponseModel<List<TokenModel>>>>{
        var token = iAuthRepo.refresh(refreshToken).toModel { it.map { it.toModel() } }
        emit(Resource.Success(token))
    }.catchHandler {
        emit(Resource.Error(it.message, it.status))
    }
}