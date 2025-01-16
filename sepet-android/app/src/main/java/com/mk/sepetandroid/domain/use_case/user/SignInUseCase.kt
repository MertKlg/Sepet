package com.mk.sepetandroid.domain.use_case.user

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toDto
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.auth.toDto
import com.mk.sepetandroid.data.mapper.auth.toModel
import com.mk.sepetandroid.data.mapper.order.toModel
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.model.auth.SignInModel
import com.mk.sepetandroid.domain.model.auth.TokenModel
import com.mk.sepetandroid.domain.repository.IAuthRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val iAuthRepo: IAuthRepo
) {
    fun invoke(signInModel: SignInModel) = flow<Resource<ResponseModel<List<TokenModel>>>>{
        val response = iAuthRepo.login(signInModel.toDto()).toModel { it.map { it.toModel() } }
        emit(Resource.Success(response))
    }.catchHandler{
        emit(Resource.Error(it.message, it.status))
    }
}