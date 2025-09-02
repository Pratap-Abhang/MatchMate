package com.abhang.matchmate.domain.usecase

import com.abhang.matchmate.domain.repository.UserDataRepository
import com.abhang.matchmate.utils.ResponseState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val repository: UserDataRepository
) {

    operator fun invoke(results: Int, page: Int, gender: String) = flow {
        try{
            emit(ResponseState.Loading())
            val userData = repository.getUserData(results, page, gender)
            emit(ResponseState.Success(userData))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}