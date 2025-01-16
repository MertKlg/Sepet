package com.mk.sepetandroid.domain.use_case.address

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.address.AddressModel
import com.mk.sepetandroid.domain.repository.IAddressRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteAddressUseCase @Inject constructor(
    private val iAddressRepository: IAddressRepo
) {
    fun invoke(accessToken : String,addressModel: AddressModel) = flow<Resource<String>>{
        emit(Resource.Loading())
        iAddressRepository.deleteAddress(accessToken, addressModel.id.toString() )
        emit(Resource.Success("Successfully deleted"))
    }.catch {
        emit(Resource.Error(it.localizedMessage))
    }
}