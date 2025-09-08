package com.abhang.matchmate.domain.usecase

import com.abhang.matchmate.domain.repository.UserDataRepository
import com.abhang.matchmate.data.handler.ResponseState
import com.abhang.matchmate.data.handler.handleError
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val repository: UserDataRepository
) {

    operator fun invoke(pageSize: Int, pageNo: Int, gender: String) = flow {
        emit(ResponseState.Loading())
        emit(repository.getUserData(pageSize, pageNo, gender))
    }

}