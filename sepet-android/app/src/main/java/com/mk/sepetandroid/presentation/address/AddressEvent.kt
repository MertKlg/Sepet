package com.mk.sepetandroid.presentation.address

import com.mk.sepetandroid.domain.model.address.AddressModel

sealed class AddressEvent {
    data object GetAddress : AddressEvent()
    data class AddAddress(val addressModel: AddressModel) : AddressEvent()
    data class DeleteAddress(val addressModel: AddressModel) : AddressEvent()
    data class UpdateAddress(val addressModel: AddressModel) : AddressEvent()

    data object WillClearUpdateAddress : AddressEvent()
    data class WillUpdateAddress(val addressModel: AddressModel) : AddressEvent()
    data object CloseDialog : AddressEvent()
}