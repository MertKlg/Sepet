package com.mk.sepetandroid.presentation.mydetails

import com.mk.sepetandroid.domain.model.profile.ProfileModel
import com.mk.sepetandroid.domain.model.view.DialogModel

data class MyDetailsState(
    val messageAvailable : Boolean = false,
    val message : String? = "",
    val profileModel: List<ProfileModel> = emptyList(),
    val dialogModel: DialogModel ?= null
)