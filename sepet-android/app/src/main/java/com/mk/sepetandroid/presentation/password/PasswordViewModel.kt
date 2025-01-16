package com.mk.sepetandroid.presentation.password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.auth.UpdatePasswordModel
import com.mk.sepetandroid.domain.model.vimodel.AbstractViewModel
import com.mk.sepetandroid.domain.use_case.password.UpdatePassUseCase
import com.mk.sepetandroid.domain.use_case.token.DeleteTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.GetTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.UpdateTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val updatePasswordUseCase: UpdatePassUseCase,

    private val getTokenUseCase: GetTokenUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase
) : AbstractViewModel<PasswordEvent>(
    getTokenUseCase,
    refreshTokenUseCase,
    updateTokenUseCase,
    deleteTokenUseCase
) {

    private val _state = mutableStateOf(PasswordState())
    val state : State<PasswordState> = _state

    private fun setNewPassword(updatePasswordModel: UpdatePasswordModel) {
        getToken(
            onFailure = {
                _state.value = _state.value.copy(
                    isError = true,
                    message = it.message
                )
            },
            onSuccess = { token ->
                updatePasswordUseCase.invoke( token.accessToken.toString(), updatePasswordModel)
                    .onEach {
                        when(it){
                            is Resource.Error -> {
                                if(it.status == 401){
                                    refreshToken(
                                        tokenModel = token,
                                        onSuccess = {
                                            setNewPassword(updatePasswordModel)
                                        },
                                        onFailure = {
                                            _state.value = _state.value.copy(
                                                isError = true,
                                                message = it.message
                                            )
                                        }
                                    )
                                }else{
                                    _state.value = _state.value.copy(
                                        isError = true,
                                        message = it.message
                                    )
                                }
                            }
                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    isSuccess = true,
                                    message = it.data.message
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            }
        )
    }

    override fun onEvent(stateType: PasswordEvent) {
        when(stateType){
            PasswordEvent.CloseDialog -> {
                _state.value = _state.value.copy(
                    isError = false,
                    isSuccess = false
                )
            }

            is PasswordEvent.SetNewPassword -> {
                setNewPassword(
                    stateType.updatePasswordModel
                )
            }
        }
    }

}