package com.mk.sepetandroid.presentation.route

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mk.sepetandroid.domain.model.enums.RouterEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(

): ViewModel(){
    private val _state = mutableStateOf(RouteState())
    val state : State<RouteState> = _state


    private fun navigate(navigate : RouterEnum){
        _state.value = state.value.copy(
            navigation = navigate.name
        )
    }


    fun onEvent(stateEvent: RouteEvent){
        when(stateEvent){
            is RouteEvent.Navigate -> {
                navigate(stateEvent.route)
            }
        }
    }

}