package com.mk.sepetandroid.presentation.cart

import com.mk.sepetandroid.domain.model.cart.CartModel
import com.mk.sepetandroid.domain.model.cart.CartProductsModel
import com.mk.sepetandroid.domain.model.address.AddressModel

sealed class CartEvent{
    data object GetCart : CartEvent()
    data object GetAddress : CartEvent()
    data class UpdateCartProduct(val cartProductsModel: CartProductsModel?) : CartEvent()
    data class TakeOrder(val cartModel: CartModel, val addressModel: AddressModel?) : CartEvent()
    data object ClearMessage : CartEvent()
}