package com.mk.sepetandroid.domain.use_case.token

import com.mk.sepetandroid.common.Resource
import com.mk.sepetandroid.domain.repository.ITokenDbRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteAllUseCase @Inject constructor(
    private val iTokenDbRepo: ITokenDbRepo
) {
    fun invoke() = flow<Resource<String>> {
        emit(Resource.Loading())
        iTokenDbRepo.deleteAll()
        emit(Resource.Success(""))
    }.catch {
        emit(Resource.Error(it.localizedMessage))
    }
}