package com.abhang.matchmate.domain.repository

import com.abhang.matchmate.data.local.model.UserData

interface UserDataLocalRepository {

    suspend fun addUser(userData: UserData)

    suspend fun addUsers(userData: List<UserData>)

    suspend fun getUserData(limit: Int, offset: Int): List<UserData>

    suspend fun checkIfUserExist(id: List<String>): List<String>

}