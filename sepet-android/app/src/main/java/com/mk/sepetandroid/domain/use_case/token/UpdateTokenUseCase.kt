package com.mk.sepetandroid.domain.use_case.token

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.auth.toEntity
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.model.auth.TokenModel
import com.mk.sepetandroid.domain.repository.ITokenDbRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateTokenUseCase @Inject constructor(
    private val tokenDbRepo: ITokenDbRepo
) {
    fun invoke(tokenModel: TokenModel) = flow<Resource<ResponseModel<List<Any>>>>{
        emit(Resource.Loading())

        tokenDbRepo.updateToken(tokenModel.toEntity())
        emit(Resource.Success(ResponseModel(data = emptyList(), status = 200, message = "Token saved")))
    }.catchHandler {
        emit(Resource.Error(it.message,it.status))
    }
}