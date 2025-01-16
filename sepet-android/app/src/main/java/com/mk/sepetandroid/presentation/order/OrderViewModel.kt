package com.mk.sepetandroid.presentation.order

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.order.OrderModel
import com.mk.sepetandroid.domain.model.order.UpdateOrderModel
import com.mk.sepetandroid.domain.model.vimodel.AbstractViewModel
import com.mk.sepetandroid.domain.use_case.address.GetAddressUseCase
import com.mk.sepetandroid.domain.use_case.order.OrderDetailUseCase
import com.mk.sepetandroid.domain.use_case.order.OrdersUseCase
import com.mk.sepetandroid.domain.use_case.order.UpdateOrderUseCase
import com.mk.sepetandroid.domain.use_case.token.DeleteTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.GetTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.UpdateTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase,
    private val getOrderUseCase: OrdersUseCase,
    private val orderDetailUseCase: OrderDetailUseCase,
    private val getAddressUseCase: GetAddressUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase
) : AbstractViewModel<OrderEvent>(
    getTokenUseCase,
    refreshTokenUseCase,
    updateTokenUseCase,
    deleteTokenUseCase,
) {
    private val _state = mutableStateOf(OrderState())
    val state : State<OrderState> = _state



    private fun init(){
        getToken(
            onFailure = {
                _state.value = _state.value.copy(
                    isError = true,
                    message = it.message
                )
            },
            onSuccess = {
                if(it.accessToken.isNullOrEmpty()){
                    refreshToken(
                        it,
                        onFailure = {
                            _state.value = _state.value.copy(
                                isError = true,
                                message = it.message
                            )
                        },
                        onSuccess = {
                            init()
                        }
                    )
                }else{
                    getOrders(it.accessToken)
                    getAddress(it.accessToken)
                }
            }
        )
    }

    private fun getOrders(accessToken : String){

        getOrderUseCase.invoke(accessToken)
            .onEach {
                when(it){
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isError = true,
                            message = it.message ?: "Something went wrong"
                        )
                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            orders = it.data.data
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getAddress(accessToken: String){
        getAddressUseCase.invoke(accessToken)
            .onEach {
                when(it){
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isError = true,
                            message = it.message ?: "Something went wrong"
                        )
                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            userAddress = it.data.data
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }


    private fun getOrderDetail(orderModel: OrderModel){
        getToken(
            onFailure = {
                _state.value = _state.value.copy(
                    isError = true,
                    message = it.message
                )
            },
            onSuccess = { tokenModel ->
                orderDetailUseCase.invoke(tokenModel.accessToken.toString(), orderModel.id)
                    .onEach {
                        when(it){
                            is Resource.Error -> {
                                if(it.status == 401){
                                    refreshToken(
                                        tokenModel,
                                        onFailure = {
                                            _state.value = _state.value.copy(
                                                isError = true,
                                                message = it.message
                                            )
                                        },
                                        onSuccess = {
                                            getOrderDetail(orderModel)
                                        }
                                    )
                                }else{
                                    _state.value = _state.value.copy(
                                        isError = true,
                                        message = it.message ?: "Something went wrong"
                                    )
                                }

                            }
                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    orderDetail = it.data.data
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            }
        )
    }

    private fun updateOrder(orderModel: OrderModel?,updateOrderModel : UpdateOrderModel){

        if(orderModel == null){
            return
        }

        getToken(
            onFailure = {
                _state.value = _state.value.copy(
                    isError = true,
                    message = it.message
                )
            },
            onSuccess = { tokenModel ->
                updateOrderUseCase.invoke(
                    tokenModel.accessToken.toString(),
                    orderModel.id,
                    updateOrderModel
                ).onEach {
                    when(it){
                        is Resource.Error -> {
                            if(it.status == 401){
                                refreshToken(
                                    tokenModel,
                                    onFailure = {
                                        _state.value = _state.value.copy(
                                            isError = true,
                                            message = it.message
                                        )
                                    },
                                    onSuccess = {
                                        getOrderDetail(orderModel)
                                    }
                                )
                            }else{
                                _state.value = _state.value.copy(
                                    isError = true,
                                    message = it.message ?: "Something went wrong"
                                )
                            }

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isError = true,
                                message = it.data.message
                            )
                            clearOrderDetails()
                        }
                    }
                }.launchIn(viewModelScope)
            }
        )

    }

    private fun clearOrderDetails(){
        _state.value = _state.value.copy(
            orderDetail = emptyList()
        )
    }

    private fun selectedOrder(orderModel: OrderModel?) {
        _state.value = _state.value.copy(
            selectedOrder = orderModel
        )
    }

    private fun showAddressDialog(showAddressDialog : Boolean){
        _state.value = _state.value.copy(
            showAddressDialog = showAddressDialog
        )
    }



    override fun onEvent(stateEvent: OrderEvent){
        when(stateEvent){
            is OrderEvent.init -> {
                init()
            }

            OrderEvent.CloseDialog -> {
                _state.value = _state.value.copy(
                    isError = false
                )
            }

            is OrderEvent.GetOrderDetail -> {
                getOrderDetail(stateEvent.orderModel)
            }

            OrderEvent.ClearDetail -> {
                clearOrderDetails()
            }

            is OrderEvent.UpdateOrder -> {
                updateOrder(stateEvent.orderModel, stateEvent.updateOrder)
            }

            is OrderEvent.SelectedOrder -> {
                selectedOrder(
                    stateEvent.orderModel
                )
            }

            is OrderEvent.ShowAddressDialog -> {
                showAddressDialog(
                    stateEvent.showAddressDialog
                )
            }

            is OrderEvent.ShowCancelDialog -> {
                _state.value = _state.value.copy(
                    cancelDialog = stateEvent.status
                )
            }
        }
    }

}