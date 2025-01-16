package com.mk.sepetandroid.domain.use_case.token

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.auth.toEntity
import com.mk.sepetandroid.domain.model.auth.TokenModel
import com.mk.sepetandroid.domain.repository.ITokenDbRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteTokenUseCase @Inject constructor(
    private val tokenDbRepo: ITokenDbRepo
) {
    fun invoke(tokenModel: TokenModel) = flow<Resource<String>>{
        tokenDbRepo.deleteToken(tokenModel.toEntity())
        emit(Resource.Success("Token successfully deleted"))
    }.catchHandler {
        emit(it)
    }
}