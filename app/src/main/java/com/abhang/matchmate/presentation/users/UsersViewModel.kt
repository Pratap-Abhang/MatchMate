package com.abhang.matchmate.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.domain.usecase.GetUserBaseOnStatusUseCase
import com.abhang.matchmate.domain.usecase.GetUserDataUseCase
import com.abhang.matchmate.domain.usecase.UpdateUserStatusUseCase
import com.abhang.matchmate.utils.ResponseState
import com.abhang.matchmate.utils.StateHandler
import com.abhang.matchmate.utils.StatusEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val updateUserStatusUseCase: UpdateUserStatusUseCase,
    private val getUserBaseOnStatusUseCase: GetUserBaseOnStatusUseCase
): ViewModel() {
    private var pageSize = 10
    private val _userValue = MutableStateFlow(StateHandler<List<UserData>>())
    var userValue : StateFlow<StateHandler<List<UserData>>> = _userValue
    private val _userStatusValue = MutableStateFlow(StateHandler<List<UserData>>())
    var userStatusValue : StateFlow<StateHandler<List<UserData>>> = _userStatusValue
    private val _userUpdateValue = MutableStateFlow(StateHandler<Int>())
    var userUpdateValue : StateFlow<StateHandler<Int>> = _userUpdateValue

    fun getUserData(pageNo: Int, gender: String) = viewModelScope.launch(Dispatchers.IO) {
        getUserDataUseCase.invoke(pageSize, pageNo, gender).collect{
            when(it){
                is ResponseState.Loading -> _userValue.value = StateHandler(isLoading = true)
                is ResponseState.Success -> _userValue.value = StateHandler(isLoading = false, data = it.data)
                is ResponseState.Error -> _userValue.value = StateHandler(isLoading = false, error = it.message.toString())
            }
        }
    }

    fun updateUserStatus(id: String, status: String) = viewModelScope.launch(Dispatchers.IO) {
        updateUserStatusUseCase.invoke(id, status).collect{
            when(it){
                is ResponseState.Loading -> _userUpdateValue.value = StateHandler(isLoading = true)
                is ResponseState.Success -> _userUpdateValue.value = StateHandler(isLoading = false, data = it.data)
                is ResponseState.Error -> _userUpdateValue.value = StateHandler(isLoading = false, error = it.message.toString())
            }
        }
    }

    fun getUserDataBaseOnStatus(status: String, offset:Int) = viewModelScope.launch(Dispatchers.IO) {
        getUserBaseOnStatusUseCase.invoke(status, pageSize, offset).collect{
            when(it){
                is ResponseState.Loading -> _userStatusValue.value = StateHandler(isLoading = true)
                is ResponseState.Success -> _userStatusValue.value = StateHandler(isLoading = false, data = it.data)
                is ResponseState.Error -> _userStatusValue.value = StateHandler(isLoading = false, error = it.message.toString())
            }
        }
    }

}