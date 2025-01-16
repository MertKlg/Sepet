package com.mk.sepetandroid.domain.use_case.address

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toDto
import com.mk.sepetandroid.data.remote.dto.ResponseDto
import com.mk.sepetandroid.domain.model.address.AddressModel
import com.mk.sepetandroid.domain.repository.IAddressRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddAddressUseCase @Inject constructor(
    private val iAddressRepo: IAddressRepo
) {
    fun invoke(accessToken : String, addressModel: AddressModel) = flow<Resource<ResponseDto<List<Any>>>> {
        emit(Resource.Loading())
        val req =  iAddressRepo.addAddress(accessToken,addressModel.toDto())

        emit(Resource.Success(req))
    }.catchHandler {
        emit(Resource.Error(it.message, it.status))
    }
}