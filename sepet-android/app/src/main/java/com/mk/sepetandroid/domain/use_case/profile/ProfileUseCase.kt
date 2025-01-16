package com.mk.sepetandroid.domain.use_case.profile

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.order.toModel
import com.mk.sepetandroid.data.mapper.profile.toModel
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.model.profile.ProfileModel
import com.mk.sepetandroid.domain.repository.IProfileRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val iProfileRepo: IProfileRepo
) {
    fun invoke(token : String) = flow<Resource<ResponseModel<List<ProfileModel>>>>{
        val catchRes = iProfileRepo.getProfile(token).toModel { it.map { it.toModel() } }
        emit(Resource.Success(catchRes))
    }.catchHandler {
        emit(Resource.Error(it.message, it.status))
    }
}