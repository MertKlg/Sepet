package com.mk.sepetandroid.presentation.address

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.address.AddressModel
import com.mk.sepetandroid.domain.model.vimodel.AbstractViewModel
import com.mk.sepetandroid.domain.use_case.address.AddAddressUseCase
import com.mk.sepetandroid.domain.use_case.address.DeleteAddressUseCase
import com.mk.sepetandroid.domain.use_case.address.GetAddressUseCase
import com.mk.sepetandroid.domain.use_case.address.UpdateAddressUseCase
import com.mk.sepetandroid.domain.use_case.token.DeleteTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.GetTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.UpdateTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val addressUseCase: AddAddressUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val updateAddressUseCase: UpdateAddressUseCase,

    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase

) : AbstractViewModel<AddressEvent>(
    getTokenUseCase,
    refreshTokenUseCase,
    updateTokenUseCase,
    deleteTokenUseCase
) {

    private val _state = mutableStateOf(AddressState())
    val state : State<AddressState> = _state

    private fun getAddress(){
        getToken(
            onFailure = {
                _state.value = _state.value.copy(
                    onStatus = true,
                    message = it.message
                )
            },
            onSuccess = { token ->
                getAddressUseCase.invoke(token.accessToken.toString())
                    .onEach {
                        when(it){
                            is Resource.Error -> {
                                if(it.status == 401){
                                    refreshToken(
                                        onFailure = {
                                            _state.value = AddressState(
                                                onStatus = true,
                                                message = it.message
                                            )
                                        },
                                        onSuccess = {
                                            getAddress()
                                        },
                                        tokenModel = token
                                    )
                                }else{
                                    _state.value = AddressState(
                                        onStatus = true,
                                        message = it.message ?: "Something went wrong"
                                    )
                                }


                            }
                            is Resource.Loading -> {}

                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    addressList = it.data.data,
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            }
        )
    }

    private fun addAddress(addressModel: AddressModel){
        getToken(
            onFailure = {
                _state.value = AddressState(
                    onStatus = true,
                    message = it.message
                )
            },
            onSuccess = { token ->
                addressUseCase.invoke(token.accessToken.toString(), addressModel)
                    .onEach {
                        when(it){
                            is Resource.Error -> {
                                if(it.status == 401){
                                    refreshToken(
                                        onSuccess = {
                                            addAddress(addressModel)
                                        },
                                        onFailure = {
                                            _state.value = _state.value.copy(
                                                onStatus = true,
                                                message = it.message
                                            )
                                        },
                                        tokenModel = token
                                    )
                                }
                                _state.value = AddressState(
                                    onStatus = true,
                                    message = it.message
                                )
                            }

                            is Resource.Loading -> {}

                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    onStatus = true,
                                    message = it.data.message
                                )
                                getAddress()
                            }
                        }
                    }.launchIn(viewModelScope)
            }

        )
    }

    private fun deleteAddress(addressModel: AddressModel){
        getToken(
            onFailure = {
                _state.value = _state.value.copy(
                    onStatus = true,
                    message = it.message
                )
            },
            onSuccess = { token ->
                deleteAddressUseCase.invoke(token.accessToken.toString(), addressModel)
                    .onEach {
                        when(it){
                            is Resource.Error -> {
                                if(it.status == 401){
                                    refreshToken(
                                        onSuccess = {
                                            addAddress(addressModel)
                                        },
                                        onFailure = {
                                            _state.value = _state.value.copy(
                                                onStatus = true,
                                                message = it.message
                                            )
                                        },
                                        tokenModel = token
                                    )
                                }else{
                                    _state.value = AddressState(
                                        onStatus = true,
                                        message = it.message
                                    )
                                }

                            }

                            is Resource.Loading -> {
                            }
                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    onStatus = true,
                                    message = it.data
                                )

                                getAddress()
                            }
                        }
                    }.launchIn(viewModelScope)
            }
        )
    }

    private fun updateAddress(addressModel: AddressModel){
        getToken(
            onFailure = {

            },
            onSuccess = { token ->
                updateAddressUseCase.invoke(token.accessToken.toString(),addressModel)
                    .onEach {
                        when(it){
                            is Resource.Error -> {
                                refreshToken(
                                    onSuccess = {
                                        addAddress(addressModel)
                                    },
                                    onFailure = {
                                        _state.value = _state.value.copy(
                                            onStatus = true,
                                            message = it.message
                                        )
                                    },
                                    tokenModel = token
                                )
                            }

                            is Resource.Loading -> {}

                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    onStatus = true,
                                    message = it.data.message
                                )
                                getAddress()
                            }
                        }
                    }.launchIn(viewModelScope)
            }
        )
    }


    override fun onEvent(stateEvent: AddressEvent){
        when(stateEvent){
            is AddressEvent.GetAddress -> {
                getAddress()
            }

            is AddressEvent.AddAddress -> {
                addAddress(stateEvent.addressModel)
            }

            is AddressEvent.DeleteAddress -> {
                deleteAddress(stateEvent.addressModel)
            }

            is AddressEvent.UpdateAddress -> {
                updateAddress(stateEvent.addressModel)
            }

            AddressEvent.CloseDialog -> {
                _state.value = _state.value.copy(
                    onStatus = false,
                    message = ""
                )
            }


            is AddressEvent.WillUpdateAddress -> {
                _state.value = _state.value.copy(
                    willUpdateAddress = stateEvent.addressModel
                )
            }

            AddressEvent.WillClearUpdateAddress -> {
                _state.value = _state.value.copy(
                    willUpdateAddress = null
                )
            }
        }
    }
}