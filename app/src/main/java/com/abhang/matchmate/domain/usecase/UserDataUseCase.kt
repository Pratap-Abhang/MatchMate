package com.abhang.matchmate.domain.usecase

import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.domain.repository.UserDataLocalRepository
import com.abhang.matchmate.domain.repository.UserDataRepository
import com.abhang.matchmate.utils.ResponseState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDataUseCase @Inject constructor(
    private val repository: UserDataRepository,
    private val localRepository: UserDataLocalRepository
) {

    operator fun invoke(results: Int) = flow {
        try{
            emit(ResponseState.Loading())
            val userData = repository.getUserData(results)
            val localData = localRepository.checkIfUserExist(userData.results.map { it.login.uuid } )
            var mList : List<UserData> = ArrayList()
            mList = if(userData.results.isNotEmpty()){
                if(localData.containsAll(userData.results.map { it.login.uuid })) {
                    localRepository.getUserData(10, 10)
                } else {
                    val pData = userData.results.filter { it.login.uuid !in localData }
                    localRepository.addUsers(pData.map { it.toUserData() })
                    localRepository.getUserData(10, 10)
                }
            } else {
                localRepository.getUserData(10, 10)
            }
            emit(ResponseState.Success(mList))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}