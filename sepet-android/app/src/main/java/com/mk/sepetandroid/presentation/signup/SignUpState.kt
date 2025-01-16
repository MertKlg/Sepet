package com.mk.sepetandroid.presentation.signup

import com.mk.sepetandroid.domain.model.state.GenericState
import com.mk.sepetandroid.domain.model.view.DialogModel

data class SignUpState(
    override val isMessageAvailable: Boolean = false,
    override val message : String = ""
) : GenericState(isMessageAvailable,message)
