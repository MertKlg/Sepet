package com.mk.sepetandroid.presentation.profile

import com.mk.sepetandroid.domain.model.state.GenericState
import com.mk.sepetandroid.domain.model.profile.ProfileModel

data class ProfileState(
    override val isMessageAvailable : Boolean = false,
    override val message : String = "",
    val profile : List<ProfileModel> = emptyList<ProfileModel>(),
    val routerEnum: String = ""
) : GenericState(isMessageAvailable,message)