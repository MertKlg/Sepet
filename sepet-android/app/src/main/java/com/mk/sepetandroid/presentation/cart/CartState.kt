package com.mk.sepetandroid.presentation.cart

import com.mk.sepetandroid.domain.model.address.AddressModel
import com.mk.sepetandroid.domain.model.cart.CartModel
import com.mk.sepetandroid.domain.model.state.GenericState

data class CartState(
    override val isMessageAvailable: Boolean = false,
    override val message : String= "",
    val cart : MutableList<CartModel> = mutableListOf(),
    val address : List<AddressModel> = emptyList()
) : GenericState()