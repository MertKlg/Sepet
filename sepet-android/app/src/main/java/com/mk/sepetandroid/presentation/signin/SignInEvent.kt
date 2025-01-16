package com.mk.sepetandroid.presentation.signin

import com.mk.sepetandroid.domain.model.auth.SignInModel

sealed class SignInEvent{
    data class SignIn(val signInModel: SignInModel) : SignInEvent()
    data object CloseDialog : SignInEvent()
}