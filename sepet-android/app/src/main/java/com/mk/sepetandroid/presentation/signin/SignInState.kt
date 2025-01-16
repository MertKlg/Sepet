package com.mk.sepetandroid.presentation.signin

import com.mk.sepetandroid.domain.model.state.GenericState

data class SignInState(
    val successSignIn : Boolean = false,
    override val isMessageAvailable: Boolean = false,
    override val message: String = ""
) : GenericState(isMessageAvailable,message)
