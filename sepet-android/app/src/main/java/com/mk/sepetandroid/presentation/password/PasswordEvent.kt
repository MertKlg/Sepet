package com.mk.sepetandroid.presentation.password

import com.mk.sepetandroid.domain.model.auth.UpdatePasswordModel

sealed class PasswordEvent {
    data class SetNewPassword(val updatePasswordModel: UpdatePasswordModel) : PasswordEvent()
    data object CloseDialog : PasswordEvent()
}