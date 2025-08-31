package com.abhang.matchmate.data.remote.repository

import com.abhang.matchmate.data.remote.model.response.User
import com.abhang.matchmate.data.remote.model.response.UserResponse
import com.abhang.matchmate.data.remote.network.INetworkService
import com.abhang.matchmate.domain.repository.UserDataRepository
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val networkApi: INetworkService
) : UserDataRepository {

    override suspend fun getUserData(results: Int): UserResponse {
        return networkApi.getUserData(results)
    }

}