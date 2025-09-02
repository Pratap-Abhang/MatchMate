package com.abhang.matchmate.domain.usecase

import com.abhang.matchmate.domain.repository.UserDataRepository
import com.abhang.matchmate.utils.ResponseState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserBaseOnStatusUseCase @Inject constructor(
    private val repository: UserDataRepository
) {

    operator fun invoke(status: String, limit: Int, offset:Int) = flow {
        try{
            emit(ResponseState.Loading())
            val userData = repository.getUserBaseOnStatus(status, limit, offset)
            emit(ResponseState.Success(userData))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}