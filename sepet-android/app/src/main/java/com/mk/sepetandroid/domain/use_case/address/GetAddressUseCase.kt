package com.mk.sepetandroid.domain.use_case.address

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.address.AddressModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.repository.IAddressRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(
    private val iAddressRepo: IAddressRepo
) {
    fun invoke(accessToken : String) = flow<Resource<ResponseModel<List<AddressModel>>>>{
        val res = iAddressRepo.getAddress(accessToken).toModel { it.map { it.toModel() } }

        emit(Resource.Success(res))
    }.catchHandler{
        emit(Resource.Error(it.message, it.status))
    }
}