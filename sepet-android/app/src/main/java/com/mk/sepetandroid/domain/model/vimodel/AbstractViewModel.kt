package com.mk.sepetandroid.domain.model.vimodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.model.auth.TokenModel
import com.mk.sepetandroid.domain.use_case.token.DeleteTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.GetTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.UpdateTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.RefreshTokenUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class AbstractViewModel<StateEvent> (
    private val tokenUseCase: GetTokenUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase
) : ViewModel(){

    protected fun getToken(
        onFailure: (ResponseModel<List<Any>>) -> Unit,
        onSuccess: (tokenModel : TokenModel) -> Unit
    ) {
        tokenUseCase.invoke()
            .onEach {
                when (it) {
                    is Resource.Error -> {
                        onFailure(
                            ResponseModel(
                                message = it.message ?: "Error fetching token",
                                status = it.status ?: 500,
                                data = emptyList()
                            )
                        )
                    }

                    is Resource.Loading -> { }

                    is Resource.Success -> {
                        if(it.data.isEmpty()){
                            onFailure(
                                ResponseModel(
                                    message = "You need a log-in again",
                                    status = 401,
                                    data = emptyList()
                                )
                            )

                        }else{
                            val tokenModel = it.data.first()
                            if (tokenModel.accessToken.isNullOrEmpty()) {
                                refreshToken(
                                    tokenModel = tokenModel,
                                    onFailure = { error ->
                                        onFailure(error)
                                    },
                                    onSuccess = {
                                        getToken(onFailure, onSuccess)
                                    }
                                )
                            } else {
                                onSuccess(tokenModel)
                            }
                        }



                    }
                }
            }
            .launchIn(viewModelScope)
    }


    protected fun refreshToken(tokenModel: TokenModel, onFailure: (ResponseModel<List<Any>>) -> Unit, onSuccess: () -> Unit){
        if(tokenModel.refreshToken.isNullOrEmpty()){
            onFailure(ResponseModel("Token expired you need a login again", 401, emptyList()))
            return
        }

        refreshTokenUseCase.invoke(tokenModel.refreshToken)
            .onEach {
                when(it){
                    is Resource.Error -> {
                        onFailure(
                            ResponseModel(
                                it.message ?: "Something went wrong",
                                it.status ?: 401,
                                emptyList()
                            )
                        )

                        if(it.status == 401){
                            deleteTokenUseCase.invoke(tokenModel)
                        }
                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        val getToken = it.data.data.first()

                        val newToken = tokenModel.copy(
                            accessToken = getToken.accessToken
                        )
                        updateToken(
                            newToken,
                            onFailure = {
                                onFailure(it)
                            },
                            onSuccess = {
                                onSuccess()
                            }
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun updateToken(tokenModel: TokenModel, onFailure: (ResponseModel<List<Any>>) -> Unit, onSuccess: () -> Unit){
        updateTokenUseCase.invoke(tokenModel)
            .onEach {
                when(it){
                    is Resource.Error -> {
                        onFailure(
                            ResponseModel(
                                it.message ?: "Something went wrong",
                                it.status ?: 500,
                                emptyList()
                            )
                        )
                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        onSuccess()
                    }
                }
            }.launchIn(viewModelScope)
    }

    abstract fun onEvent(stateEvent: StateEvent)

}