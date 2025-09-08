package com.abhang.matchmate.domain.repository

import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.data.handler.ResponseState

interface UserDataRepository {

    suspend fun getUserData(pageSize: Int, pageNo : Int, gender: String): ResponseState<List<UserData>>

//    suspend fun updateUserStatus(id: String, status: String): ResponseState<Int>
//
//    suspend fun getUserBaseOnStatus(newStatus: String, limit: Int, offset:Int): ResponseState<List<UserData>>
}