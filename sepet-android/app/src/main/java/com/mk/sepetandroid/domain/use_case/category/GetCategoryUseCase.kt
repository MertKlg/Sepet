package com.mk.sepetandroid.domain.use_case.category

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.common.catchHandler
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.mapper.category.toModel
import com.mk.sepetandroid.data.mapper.response.toModel
import com.mk.sepetandroid.domain.model.response.CategoryModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.repository.ICategoryRepo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val iCategoryRepo: ICategoryRepo
) {
    fun invoke(id : String?) = flow<Resource<ResponseModel<List<CategoryModel>>>>{
        emit(Resource.Success(iCategoryRepo.getCategories(id).toModel { it.map { it.toModel() } }))
    }.catchHandler {
        emit(Resource.Error(it.message,it.status))
    }
}