package com.mk.sepetandroid.presentation.signin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.auth.SignInModel
import com.mk.sepetandroid.domain.model.auth.TokenModel
import com.mk.sepetandroid.domain.use_case.token.AddTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val addTokenUseCase: AddTokenUseCase

) : ViewModel() {
    private val _state = mutableStateOf(SignInState())
    val state : State<SignInState> = _state


    private fun signIn(signInModel: SignInModel){
        signInUseCase.invoke(signInModel)
            .onEach {
                when(it){
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isMessageAvailable = true,
                            message = it.message ?: "Something went wrong"
                        )
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val token = it.data.data.first()

                        if(!token.accessToken.isNullOrEmpty() || !token.refreshToken.isNullOrEmpty()){
                            saveToken(it.data.data.first())
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun saveToken(tokenModel: TokenModel){
        addTokenUseCase.invoke(tokenModel)
            .onEach {
                when(it){
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isMessageAvailable = true,
                            message = it.message ?: "Something went wrong"
                        )
                    }

                    is Resource.Loading -> {}

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            successSignIn = true,
                            isMessageAvailable = true,
                            message = "Successfully signed in"
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }


    fun onEvent(event : SignInEvent){
        when(event){
            is SignInEvent.SignIn -> {
                signIn(event.signInModel)
            }

            is SignInEvent.CloseDialog -> {
                _state.value = state.value.copy(

                )
            }
        }
    }
}