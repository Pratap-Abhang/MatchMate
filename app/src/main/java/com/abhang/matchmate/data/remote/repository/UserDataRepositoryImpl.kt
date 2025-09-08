package com.abhang.matchmate.data.remote.repository

import com.abhang.matchmate.data.handler.ErrorHandler
import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.data.remote.network.INetworkService
import com.abhang.matchmate.domain.repository.UserDataLocalRepository
import com.abhang.matchmate.domain.repository.UserDataRepository
import com.abhang.matchmate.data.handler.ResponseState
import com.abhang.matchmate.data.handler.ResponseState.*
import com.abhang.matchmate.data.handler.safeApiCall
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val networkApi: INetworkService,
    private val localRepository: UserDataLocalRepository
) : UserDataRepository {

    override suspend fun getUserData(pageSize: Int, pageNo: Int, gender: String): ResponseState<List<UserData>> {

        val offset = pageNo * pageSize
        val localUsers = localRepository.getUserData(pageSize, offset)
        return if (localUsers.size == pageSize) {
            Success(localUsers)
        } else {
            val apiPageSize = pageSize - localUsers.size
            val apiUsers = safeApiCall { networkApi.getUserData(apiPageSize, pageNo, gender) }
            when(apiUsers){
                is Success -> {
                    val pData = apiUsers.data?.results?.filter { it.login.uuid !in localUsers.map { it1-> it1.userId } }
                    localRepository.addUsers( pData!!.map { it.toUserData() })
                    Success(localRepository.getUserData(pageSize, pageNo))
                }
                is Error -> { Error(apiUsers.code, apiUsers.message, null) }
                is Loading -> Loading()
            }
        }
//        return mData
    }

//    override suspend fun updateUserStatus(id: String, status: String): ResponseState<Int, ErrorHandler> {
//        return ResponseState.Success(localRepository.updateStatus(id, status))
//    }
//
//    override suspend fun getUserBaseOnStatus(newStatus: String, limit: Int, offset:Int): ResponseState<List<UserData>, ErrorHandler> {
//        return ResponseState.Success(localRepository.getUserBaseOnStatus(newStatus, limit, offset))
//    }

}