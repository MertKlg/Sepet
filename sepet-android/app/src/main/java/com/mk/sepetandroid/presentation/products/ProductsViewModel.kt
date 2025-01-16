package com.mk.sepetandroid.presentation.products


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.model.response.AddProductModel
import com.mk.sepetandroid.domain.model.response.CategoryModel
import com.mk.sepetandroid.domain.model.response.ProductModel
import com.mk.sepetandroid.domain.model.vimodel.AbstractViewModel
import com.mk.sepetandroid.domain.use_case.cart.AddToCartUseCase
import com.mk.sepetandroid.domain.use_case.category.GetCategoryUseCase
import com.mk.sepetandroid.domain.use_case.product.GetProductsUseCase
import com.mk.sepetandroid.domain.use_case.token.DeleteTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.GetTokenUseCase
import com.mk.sepetandroid.domain.use_case.token.UpdateTokenUseCase
import com.mk.sepetandroid.domain.use_case.user.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private var getProductsUseCase: GetProductsUseCase,
    private var addToCartUseCase: AddToCartUseCase,
    private var getTokenUseCase: GetTokenUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase
) : AbstractViewModel<ProductsEvent>(
    getTokenUseCase,
    refreshTokenUseCase,
    updateTokenUseCase,
    deleteTokenUseCase
) {

    private val _state = mutableStateOf(ProductsState())
    val state : State<ProductsState> = _state

    private fun getProducts(
        min : Int,
        max : Int,
        minPrice : String = "",
        maxPrice : String = "",
        category : List<CategoryModel> = emptyList()
    ){
        getProductsUseCase.invoke(min, max, minPrice.toDoubleOrNull(), maxPrice.toDoubleOrNull(), category.joinToString(",") { it.id })
            .onEach {
                when(it){
                    is Resource.Error -> {
                        _state.value = ProductsState(
                            onStatus = true,
                            message = it.message ?: "Something went wrong",
                        )
                    }

                    is Resource.Loading -> {}

                    is Resource.Success -> {
                        val res = it.data.data
                        val products = res.first().products

                        if (products.isEmpty()) {
                            _state.value = _state.value.copy(isEndOfList = true)
                        } else {
                            val currentProducts = _state.value.products.toMutableList()
                            currentProducts.addAll(products)

                            _state.value = _state.value.copy(
                                products = currentProducts,
                                min = min,
                                max = max
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun addToCart(productModel: ProductModel){
        getToken(
            onSuccess = { tokenModel ->
                addToCartUseCase.invoke(tokenModel.accessToken!!, AddProductModel(productModel.id.toString(), 1))
                    .onEach {
                        when(it){
                            is Resource.Error -> {
                                if(it.status == 401){
                                    refreshToken(
                                        onFailure = {
                                            _state.value = _state.value.copy(
                                                onStatus = true,
                                                message = it.message
                                            )
                                        },
                                        tokenModel = tokenModel,
                                        onSuccess = {
                                            addToCart(productModel)
                                        }
                                    )
                                }else{
                                    _state.value = _state.value.copy(
                                        onStatus = true,
                                        categories = _state.value.categories,
                                        products = _state.value.products,
                                        message = it.message ?: "Something went wrong"
                                    )
                                }

                            }
                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {
                                _state.value = _state.value.copy(
                                    onStatus = true,
                                    message = it.data.message
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            },
            onFailure = {
                _state.value = _state.value.copy(
                    onStatus = true,
                    message = it.message
                )
            }
        )
    }

    private fun changeShowFilters(status : Boolean){
        _state.value = _state.value.copy(
            showFilters = status
        )
    }

    private fun getCategories(){
        getCategoryUseCase.invoke(null)
            .onEach {
                when(it){
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            onStatus = true,
                            message = it.message ?: "Something went wrong"
                        )
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            categories = it.data.data
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    override fun onEvent(stateEvent: ProductsEvent) {
        when(stateEvent){
            is ProductsEvent.GetProducts -> {

                getProducts(
                    stateEvent.min,
                    stateEvent.max,
                    stateEvent.minPrice,
                    stateEvent.maxPrice,
                    stateEvent.category
                )

            }

            is ProductsEvent.AddToCart -> {
                addToCart(stateEvent.productModel)
            }

            ProductsEvent.CloseDialog -> {
                _state.value = _state.value.copy(
                    onStatus = false
                )
            }

            is ProductsEvent.ShowFilters -> {
                changeShowFilters(stateEvent.status)
            }

            ProductsEvent.GetCategories -> {
                getCategories()
            }

            is ProductsEvent.FilterCategories -> {
                val updatedCategories = _state.value.filterCategories.toMutableList().apply {
                    if (contains(stateEvent.categoryModel)) {
                        remove(stateEvent.categoryModel)
                    } else {
                        add(stateEvent.categoryModel)
                    }
                }

                _state.value = _state.value.copy(
                    filterCategories = updatedCategories
                )
            }

            is ProductsEvent.ChangeMaxPrice -> {
                _state.value = _state.value.copy(
                    maxPrice = stateEvent.price
                )
            }

            is ProductsEvent.ChangeMinPrice -> {
                _state.value = _state.value.copy(
                    minPrice = stateEvent.price
                )
            }

            ProductsEvent.ClearProducts -> {
                _state.value = _state.value.copy(
                    products = mutableListOf(),
                    isEndOfList = false
                )
            }
        }
    }
}