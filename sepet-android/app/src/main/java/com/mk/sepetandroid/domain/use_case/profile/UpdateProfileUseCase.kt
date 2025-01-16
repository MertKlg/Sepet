package com.mk.sepetandroid.domain.use_case.profile

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toDto
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.profile.toDto
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.model.profile.ProfileModel
import com.mk.sepetandroid.domain.repository.IProfileRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val iProfileRepo: IProfileRepo
) {
    fun invoke(token : String , profileModel: ProfileModel) = flow<Resource<ResponseModel<List<Any>>>>{
        val res = iProfileRepo.updateProfile(token, profileModel.toDto()).toModel()

        emit(Resource.Success(res))
    }.catchHandler {
        emit(Resource.Error(it.message,it.status))
    }
}