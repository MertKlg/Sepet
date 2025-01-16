package com.mk.sepetandroid.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.auth.RegisterModel
import com.mk.sepetandroid.domain.use_case.user.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel() {
    private val _state = mutableStateOf(SignUpState())
    val state : State<SignUpState> = _state

    private fun signUp(registerModel: RegisterModel){
        signUpUseCase.invoke(registerModel)
            .onEach {
                when(it){
                    is Resource.Loading -> {}

                    is Resource.Error -> {
                        _state.value = SignUpState(
                            isMessageAvailable = true,
                            message = it.message ?: "Something went wrong"
                        )
                    }

                    is Resource.Success -> {
                        _state.value = SignUpState(
                            isMessageAvailable = true,
                            message = it.data.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onEvent(onEvent: SignUpEvent){
        when(onEvent){
            is SignUpEvent.SignUp -> {
                signUp(onEvent.registerModel)
            }

            is SignUpEvent.CloseMessage -> {
                _state.value = SignUpState(
                    isMessageAvailable = false,
                    message = ""
                )
            }
        }
    }

}