package com.abhang.matchmate.data.remote.repository

import android.util.Log
import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.data.remote.network.INetworkService
import com.abhang.matchmate.domain.repository.UserDataLocalRepository
import com.abhang.matchmate.domain.repository.UserDataRepository
import com.abhang.matchmate.utils.NetworkHelper
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val networkApi: INetworkService,
    private val localRepository: UserDataLocalRepository,
    private val networkHelper: NetworkHelper
) : UserDataRepository {

    override suspend fun getUserData(pageSize: Int, pageNo: Int, gender: String): List<UserData> {
        val offset = pageNo * pageSize
        val localUsers = localRepository.getUserData(pageSize, offset)
        val totalUsersInDb = localRepository.getUserCount()
        val mData = if (localUsers.size == pageSize) {
            // Enough data in DB
            localUsers
        } else {
            // Not enough data, call API to fetch more
            val apiPageSize = pageSize - localUsers.size
            val apiPageNumber = pageNo

            val apiUsers = networkApi.getUserData(apiPageSize, apiPageNumber, gender)
            val pData = apiUsers.results.filter { it.login.uuid !in localUsers.map { it.userId } }
            localRepository.addUsers( pData.map { it.toUserData() })


            Log.e("---->", localRepository.getUserCount().toString())
            // Return combined data
            localRepository.getUserData(pageSize, pageNo)
        }
        return mData



//        return try{
//            val cnt = localRepository.getUserCount()
//            if(cnt > pageSize*pageNo){
//                localRepository.getUserData(pageSize, pageNo)
//            }else {
//                if(networkHelper.isNetworkAvailable()){
//                    val mResponse = networkApi.getUserData(pageSize, pageNo, gender)
//                    val localData = localRepository.checkIfUserExist(mResponse.results.map{ it.toUserData().userId} )
//                    if(localData.containsAll(mResponse.results.map { it.login.uuid })) {
//                        localRepository.getUserData(pageSize, pageNo)
//                    } else {
//                        val pData = mResponse.results.filter { it.login.uuid !in localData }
//                        localRepository.addUsers(pData.map { it.toUserData() })
//                        localRepository.getUserData(pageSize, pageNo)
//                    }
//                }else{
//                    localRepository.getUserData(pageSize, pageNo)
//                }
//            }
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            localRepository.getUserData(pageSize, pageNo)
//        }


    }

    override suspend fun updateUserStatus(id: String, status: String): Int {
        return localRepository.updateStatus(id, status)
    }

    override suspend fun getUserBaseOnStatus(newstatus: String, limit: Int, offset:Int): List<UserData> {
        return localRepository.getUserBaseOnStatus(newstatus, limit, offset)
    }

}