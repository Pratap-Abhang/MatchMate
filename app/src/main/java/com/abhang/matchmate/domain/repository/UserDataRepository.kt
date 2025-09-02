package com.abhang.matchmate.domain.repository

import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.data.remote.model.response.User
import com.abhang.matchmate.data.remote.model.response.UserResponse
import com.abhang.matchmate.utils.StatusEnum

interface UserDataRepository {

    suspend fun getUserData(results: Int, page : Int, gender: String): List<UserData>

    suspend fun updateUserStatus(id: String, status: String): Int

    suspend fun getUserBaseOnStatus(newstatus: String, limit: Int, offset:Int): List<UserData>
}