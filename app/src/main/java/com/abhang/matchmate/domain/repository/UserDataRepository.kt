package com.abhang.matchmate.domain.repository

import com.abhang.matchmate.data.remote.model.response.User
import com.abhang.matchmate.data.remote.model.response.UserResponse

interface UserDataRepository {

    suspend fun getUserData(results: Int): UserResponse
}