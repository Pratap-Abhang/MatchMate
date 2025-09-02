package com.abhang.matchmate.data.remote.repository

import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.data.remote.model.response.User
import com.abhang.matchmate.data.remote.model.response.UserResponse
import com.abhang.matchmate.data.remote.network.INetworkService
import com.abhang.matchmate.domain.repository.UserDataLocalRepository
import com.abhang.matchmate.domain.repository.UserDataRepository
import com.abhang.matchmate.utils.NetworkHelper
import com.abhang.matchmate.utils.StatusEnum
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val networkApi: INetworkService,
    private val localRepository: UserDataLocalRepository,
    private val networkHelper: NetworkHelper
) : UserDataRepository {

    override suspend fun getUserData(results: Int, page: Int): List<UserData> {
        return try{
            if(networkHelper.isNetworkAvailable()){
                val mResponse = networkApi.getUserData(results, page)
                val localData = localRepository.checkIfUserExist(mResponse.results.map{ it.toUserData().userId} )
                if(localData.containsAll(mResponse.results.map { it.login.uuid })) {
                    localRepository.getUserData(results, page)
                } else {
                    val pData = mResponse.results.filter { it.login.uuid !in localData }
                    localRepository.addUsers(pData.map { it.toUserData() })
                    localRepository.getUserData(results, page)
                }
            }else{
                localRepository.getUserData(results, page)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            localRepository.getUserData(results, page)
        }


    }

    override suspend fun updateUserStatus(id: String, status: String): Int {
        return localRepository.updateStatus(id, status)
    }

    override suspend fun getUserBaseOnStatus(newstatus: String, limit: Int, offset:Int): List<UserData> {
        return localRepository.getUserBaseOnStatus(newstatus, limit, offset)
    }

}