package com.mk.sepetandroid.presentation.mydetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.profile.ProfileModel
import com.mk.sepetandroid.domain.model.vimodel.AbstractViewModel
import com.mk.sepetandroid.domain.use_case.profile.ProfileUseCase
import com.mk.sepetandroid.domain.use_case.profile.UpdateProfileUseCase
import com.mk.sepetandroid.domain.use_case.token.DeleteTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.GetTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.UpdateTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyDetailsViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,

    private val getTokenUseCase: GetTokenUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase
) : AbstractViewModel<MyDetailsEvent>(
    getTokenUseCase,
    refreshTokenUseCase,
    updateTokenUseCase,
    deleteTokenUseCase,
) {
    private val _state = mutableStateOf(MyDetailsState())
    val state : State<MyDetailsState> = _state

    private fun updateProfile(profileModel: ProfileModel){
        getToken(
            onFailure = {
                _state.value = _state.value.copy(
                    messageAvailable = true,
                    message = it.message
                )

            },

            onSuccess = { token ->
                updateProfileUseCase.invoke(token.accessToken.toString(), profileModel)
                    .onEach {
                        when(it){
                            is Resource.Error -> {
                                if(it.status == 401){
                                    refreshToken(
                                        tokenModel = token,
                                        onFailure = {
                                            _state.value = _state.value.copy(
                                                messageAvailable = true,
                                                message = it.message ?: "Something went wrong"
                                            )
                                        },
                                        onSuccess = {
                                            getProfile()
                                        }
                                    )
                                }else{
                                    _state.value = _state.value.copy(
                                        messageAvailable = true,
                                        message = it.message ?: "Something went wrong"
                                    )
                                }

                            }

                            is Resource.Loading -> {}

                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    messageAvailable = true,
                                    message = it.data.message
                                )
                                getProfile()
                            }
                        }
                    }.launchIn(viewModelScope)
            }
        )
    }

    private fun getProfile(){
        getToken(
            onFailure = {
                _state.value = _state.value.copy(
                    messageAvailable = true,
                    message = it.message
                )
            },
            onSuccess = { token ->
                profileUseCase.invoke(token.accessToken.toString())
                    .onEach {
                        when(it){
                            is Resource.Error -> {
                                if(it.status == 401){
                                    refreshToken(
                                        tokenModel = token,
                                        onFailure = {
                                            _state.value = _state.value.copy(
                                                messageAvailable = true,
                                                message = it.message ?: "Something went wrong"
                                            )
                                        },
                                        onSuccess = {
                                            getProfile()
                                        }
                                    )
                                }else{
                                    _state.value = _state.value.copy(
                                        messageAvailable = true,
                                        message = it.message ?: "Something went wrong"
                                    )
                                }
                            }

                            is Resource.Loading -> {}

                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    profileModel = it.data.data
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            }
        )
    }


    override fun onEvent(stateEvent: MyDetailsEvent){
        when(stateEvent){
            is MyDetailsEvent.UpdateProfile -> {
                updateProfile(stateEvent.profileModel)
            }

            MyDetailsEvent.GetProfile -> {
                getProfile()
            }

            MyDetailsEvent.CloseMessage -> {
                _state.value = MyDetailsState(
                    messageAvailable = false,
                    message = null
                )
            }
        }
    }
}