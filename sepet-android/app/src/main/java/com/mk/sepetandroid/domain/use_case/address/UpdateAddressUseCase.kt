package com.mk.sepetandroid.domain.use_case.address

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toDto
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.address.AddressModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.repository.IAddressRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateAddressUseCase @Inject constructor(
    private val iAddressRepo: IAddressRepo
) {

    fun invoke(accessToken : String, addressModel: AddressModel) = flow<Resource<ResponseModel<List<Any>>>>{
        emit(Resource.Loading())
        val res = iAddressRepo.updateAddress(accessToken,addressModel.toDto()).toModel()
        emit(Resource.Success(res))
    }.catchHandler {
        emit(Resource.Error(it.message, it.status))
    }
}