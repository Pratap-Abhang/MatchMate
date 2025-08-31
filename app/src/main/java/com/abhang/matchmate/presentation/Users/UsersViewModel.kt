package com.abhang.matchmate.presentation.Users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.domain.usecase.UserDataUseCase
import com.abhang.matchmate.utils.ResponseState
import com.abhang.matchmate.utils.StateHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDataUseCase: UserDataUseCase
): ViewModel() {
    private var currPage = 10
    private val _userValue = MutableStateFlow(StateHandler<List<UserData>>())
    var userValue : StateFlow<StateHandler<List<UserData>>> = _userValue

    fun getUserData() = viewModelScope.launch(Dispatchers.IO) {
        if(currPage == 5){
            return@launch
        }
        userDataUseCase(currPage).collect{
            when(it){
                is ResponseState.Loading -> _userValue.value = StateHandler(isLoading = true)
                is ResponseState.Success -> {
                    currPage++
                    _userValue.value = StateHandler(isLoading = false, data = it.data)
                }
                is ResponseState.Error -> _userValue.value = StateHandler(isLoading = false, error = it.message.toString())
            }
        }
    }
}