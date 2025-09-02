package com.abhang.matchmate.domain.usecase

import com.abhang.matchmate.domain.repository.UserDataRepository
import com.abhang.matchmate.utils.ResponseState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserStatusUseCase @Inject constructor(
    private val repository: UserDataRepository
) {

    operator fun invoke(id: String, status: String) = flow {
        try{
            emit(ResponseState.Loading())
            val userData = repository.updateUserStatus(id, status)
            emit(ResponseState.Success(userData))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}