package com.mk.sepetandroid.presentation.signup

import com.mk.sepetandroid.domain.model.auth.RegisterModel

sealed class SignUpEvent {
    data class SignUp(val registerModel: RegisterModel) : SignUpEvent()
    data object CloseMessage: SignUpEvent()
}