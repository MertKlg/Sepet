package com.mk.sepetandroid.domain.use_case.password

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.password.toUpdatePasswordDto
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.auth.UpdatePasswordModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.repository.IPasswordRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePassUseCase @Inject constructor(
    private val iPasswordRepo: IPasswordRepo
) {

    fun invoke(accessToken : String, updatePasswordModel: UpdatePasswordModel) = flow<Resource<ResponseModel<List<Any>>>>{
        iPasswordRepo.rePassword(accessToken,updatePasswordModel.toUpdatePasswordDto()).toModel().let {
            emit(Resource.Success(it))
        }
    }.catchHandler {
        emit(Resource.Error(it.message, it.status))
    }


}