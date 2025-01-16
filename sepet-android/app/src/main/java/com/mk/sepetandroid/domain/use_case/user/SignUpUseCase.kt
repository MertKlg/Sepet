package com.mk.sepetandroid.domain.use_case.user

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.auth.toDto
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.auth.RegisterModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.repository.IAuthRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val iAuthRepo: IAuthRepo
) {
    fun invoke(registerModel: RegisterModel) = flow<Resource<ResponseModel<List<Any>>>>{
        val res = iAuthRepo.register(registerModel.toDto()).toModel()
        emit(Resource.Success(res))
    }.catchHandler{
        emit(Resource.Error(it.message,it.status))
    }
}