package com.mk.sepetandroid.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.enums.RouterEnum
import com.mk.sepetandroid.domain.model.vimodel.AbstractViewModel
import com.mk.sepetandroid.domain.use_case.profile.ProfileUseCase
import com.mk.sepetandroid.domain.use_case.token.DeleteTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.GetTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.UpdateTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getProfileUseCase: ProfileUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase
) : AbstractViewModel<ProfileEvent>(
    getTokenUseCase,
    refreshTokenUseCase,
    updateTokenUseCase,
    deleteTokenUseCase
){
    private val _state = mutableStateOf(ProfileState())
    val state : State<ProfileState> = _state

    private fun getProfile(){
        getToken(
            onFailure = {
                if(it.status == 401){
                    _state.value = _state.value.copy(
                        routerEnum = RouterEnum.SIGN_IN.name
                    )
                }else{
                    _state.value = _state.value.copy(
                        isMessageAvailable = true,
                        message = it.message
                    )
                }

            },
            onSuccess = { token ->
                getProfileUseCase.invoke(token.accessToken.toString())
                    .onEach {
                        when(it){
                            is Resource.Error -> {
                                _state.value = state.value.copy(
                                    isMessageAvailable = true,
                                    message = it.message ?: "Something went wrong"
                                )
                            }
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    profile = it.data.data
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            }
        )
    }



    override fun onEvent(stateEvent: ProfileEvent){
        when(stateEvent){
            is ProfileEvent.GetProfile -> {
                getProfile()
            }

            ProfileEvent.CloseMessage -> {
                _state.value = _state.value.copy(
                    isMessageAvailable = false,
                    message = ""
                )
            }
        }
    }
}