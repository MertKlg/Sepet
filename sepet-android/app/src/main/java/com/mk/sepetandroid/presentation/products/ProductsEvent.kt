package com.mk.sepetandroid.presentation.products

import com.mk.sepetandroid.domain.model.response.CategoryModel
import com.mk.sepetandroid.domain.model.response.ProductModel

sealed class ProductsEvent {
    data class GetProducts(
        val min : Int,
        val max : Int,
        val minPrice : String = "",
        val maxPrice : String = "",
        val category : List<CategoryModel> = emptyList()
    ) : ProductsEvent()

    data class AddToCart(val productModel: ProductModel) : ProductsEvent()

    data object CloseDialog : ProductsEvent()
    data object GetCategories : ProductsEvent()

    data class ChangeMinPrice(val price : String) : ProductsEvent()
    data class ChangeMaxPrice(val price : String) : ProductsEvent()


    data class ShowFilters(val status : Boolean) : ProductsEvent()
    data class FilterCategories(val categoryModel: CategoryModel) : ProductsEvent()
    data object ClearProducts : ProductsEvent()
}