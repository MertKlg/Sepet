package com.mk.sepetandroid.domain.use_case.token

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.auth.toModel
import com.mk.sepetandroid.domain.model.auth.TokenModel
import com.mk.sepetandroid.domain.repository.ITokenDbRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val tokenDbRepo: ITokenDbRepo
) {
    fun invoke() = flow<Resource<List<TokenModel>>>{
        emit(Resource.Success(tokenDbRepo.getTokens().map { it.toModel() }))
    }.catchHandler {
        emit(Resource.Error(it.message, it.status))
    }
}