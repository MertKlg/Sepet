package com.mk.sepetandroid.presentation.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.cart.CartModel
import com.mk.sepetandroid.domain.model.cart.CartProductsModel
import com.mk.sepetandroid.domain.model.cart.UpdateCartModel
import com.mk.sepetandroid.domain.model.address.AddressModel
import com.mk.sepetandroid.domain.model.vimodel.AbstractViewModel
import com.mk.sepetandroid.domain.use_case.address.GetAddressUseCase
import com.mk.sepetandroid.domain.use_case.cart.GetCartUseCase
import com.mk.sepetandroid.domain.use_case.cart.UpdateCartUseCase
import com.mk.sepetandroid.domain.use_case.order.TakeOrderUseCase
import com.mk.sepetandroid.domain.use_case.token.DeleteTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.GetTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.UpdateTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val addressUseCase: GetAddressUseCase,
    private val takeOrderUseCase: TakeOrderUseCase,

    private val getTokenUseCase: GetTokenUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase

): AbstractViewModel<CartEvent>(
    getTokenUseCase,
    refreshTokenUseCase,
    updateTokenUseCase,
    deleteTokenUseCase
) {

    private val _state = mutableStateOf(CartState())
    val state : State<CartState> = _state

    private fun getCart(){
        getToken(
            onFailure = {
                _state.value = _state.value.copy(
                    isMessageAvailable = true,
                    message = it.message
                )
            },
            onSuccess = { token ->
                getCartUseCase.invoke(token.accessToken.toString())
                    .onEach {
                        when(it){
                            is Resource.Error -> {
                                if(it.status == 401){
                                    refreshToken(
                                        onFailure = {
                                            _state.value = _state.value.copy(
                                                isMessageAvailable = true,
                                                message = it.message
                                            )
                                        },
                                        tokenModel = token ,
                                        onSuccess = {
                                            getCart()
                                        }
                                    )
                                }else{
                                    _state.value = _state.value.copy(
                                        isMessageAvailable = true,
                                        message = it.message ?: "Something went wrong"
                                    )
                                }

                            }
                            is Resource.Loading -> {}

                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    isMessageAvailable = false,
                                    cart = it.data.data.toMutableList()
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            }
        )
    }


    private fun updateCart(productsModel: CartProductsModel?) {
        getToken(
            onFailure = {
                _state.value = _state.value.copy(
                    isMessageAvailable = true,
                    message = it.message
                )
            },
            onSuccess = { token ->
                updateCartUseCase.invoke(
                    token.accessToken.toString(),
                    _state.value.cart.first().id!!,
                    UpdateCartModel(
                        product = productsModel?.product?.id.toString(),
                        count = productsModel?.count ?: 0
                    )
                ).onEach {
                    when(it){
                        is Resource.Error -> {
                            if (it.status == 401){
                                refreshToken(
                                    onFailure = {
                                        _state.value = _state.value.copy(
                                            isMessageAvailable = true,
                                            message = it.message
                                        )
                                    },
                                    tokenModel = token ,
                                    onSuccess = {
                                        getCart()
                                    }
                                )
                            }else{
                                _state.value = _state.value.copy(
                                    isMessageAvailable = true,
                                    message = it.message ?: "Something went wrong"
                                )
                            }
                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            getCart()
                        }
                    }
                }.launchIn(viewModelScope)
            }
        )
    }

    private fun getAddress(){
        getToken(
            onFailure = {
                _state.value = _state.value.copy(
                    isMessageAvailable = true,
                    message = it.message
                )
            },
            onSuccess = { tokenModel ->
                addressUseCase.invoke(tokenModel.accessToken.toString())
                    .onEach {
                        when(it){
                            is Resource.Error -> {
                                if(it.status == 401){
                                    refreshToken(
                                        tokenModel = tokenModel,
                                        onFailure = {
                                            _state.value = _state.value.copy(
                                                isMessageAvailable = true,
                                                message = it.message
                                            )
                                        },
                                        onSuccess = {
                                            getAddress()
                                        }
                                    )
                                }else{
                                    _state.value = _state.value.copy(
                                        isMessageAvailable = true,
                                        message = it.message ?: "Something went wrong"
                                    )
                                }

                            }
                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    address = it.data.data
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            }
        )
    }

    private fun takeOrder(cartModel: CartModel, addressModel: AddressModel?){
        if(addressModel == null){
            _state.value = _state.value.copy(
                isMessageAvailable = true,
                message = "No address selected"
            )
            return
        }


        getToken(
            onFailure = {
                _state.value = _state.value.copy(
                    isMessageAvailable = true,
                    message = it.message
                )
            },
            onSuccess = { token ->

                val message = if(token.accessToken.isNullOrEmpty()){
                    "You can retry login"
                }else if(cartModel.id.isNullOrEmpty()){
                    "No cart founded"
                }else if(addressModel.id.isNullOrEmpty()){
                    "No address founded"
                }else {
                    ""
                }

                if(message.isNotEmpty() && message.isNotBlank()){
                    _state.value = _state.value.copy(
                        isMessageAvailable = true,
                        message = message
                    )
                }else{
                    takeOrderUseCase.invoke(token.accessToken.toString(), cartModel.id.toString(), addressModel.id.toString())
                        .onEach {
                            when(it){
                                is Resource.Error -> {
                                    if(it.status == 401){
                                        refreshToken(
                                            tokenModel = token,
                                            onSuccess = {
                                                takeOrder(cartModel, addressModel)
                                            },
                                            onFailure = {
                                                _state.value = _state.value.copy(
                                                    isMessageAvailable = true,
                                                    message = it.message
                                                )
                                            }
                                        )
                                    }else{
                                        _state.value = _state.value.copy(
                                            isMessageAvailable = true,
                                            message = it.message ?: "Something went wrong"
                                        )
                                    }
                                }

                                is Resource.Loading -> {}

                                is Resource.Success -> {
                                    _state.value = _state.value.copy(
                                        isMessageAvailable = true,
                                        message = it.data.message,
                                        cart = mutableListOf(),
                                        address = emptyList()
                                    )
                                }
                            }
                        }.launchIn(viewModelScope)
                }
            }
        )
    }

    private fun clearMessage(){
        _state.value = _state.value.copy(
            isMessageAvailable = false
        )
    }



    override fun onEvent(stateEvent: CartEvent){
        when(stateEvent){
            is CartEvent.GetCart -> {
                getCart()
            }

            is CartEvent.GetAddress -> {
                getAddress()
            }


            is CartEvent.UpdateCartProduct -> {
                updateCart(stateEvent.cartProductsModel)
            }

            CartEvent.ClearMessage -> {
                clearMessage()
            }

            is CartEvent.TakeOrder -> {
                takeOrder(stateEvent.cartModel, stateEvent.addressModel)
            }
        }
    }

}