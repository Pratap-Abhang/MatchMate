package com.abhang.matchmate.data.local.repository

import com.abhang.matchmate.data.local.dao.UserDao
import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.domain.repository.UserDataLocalRepository
import javax.inject.Inject

class UserDataLocalRepositoryImpl @Inject constructor(
    private val userDao: UserDao
): UserDataLocalRepository {
    override suspend fun addUser(userData: UserData) {
        userDao.addUser(userData)
    }

    override suspend fun addUsers(userData: List<UserData>) {
        userDao.addUsers(userData)
    }

    override suspend fun getUserData(limit: Int, offset: Int): List<UserData> {
        return userDao.getData(limit, offset)
    }

    override suspend fun checkIfUserExist(ids: String): String {
        return userDao.checkIfUserExist(ids)
    }

}