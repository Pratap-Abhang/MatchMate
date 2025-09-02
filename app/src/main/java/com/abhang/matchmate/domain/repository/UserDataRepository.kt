package com.abhang.matchmate.domain.repository

import com.abhang.matchmate.data.local.model.UserData

interface UserDataRepository {

    suspend fun getUserData(results: Int, page : Int, gender: String): List<UserData>

    suspend fun updateUserStatus(id: String, status: String): Int

    suspend fun getUserBaseOnStatus(newstatus: String, limit: Int, offset:Int): List<UserData>
}