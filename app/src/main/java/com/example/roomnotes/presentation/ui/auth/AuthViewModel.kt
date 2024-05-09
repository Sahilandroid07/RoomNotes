package com.example.roomnotes.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomnotes.data.local.db.entities.User
import com.example.roomnotes.data.local.repository.UsersRepository
import com.example.roomnotes.domain.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _registerLiveData = MutableLiveData<DataState<User>>()
    val registerLiveData: LiveData<DataState<User>> = _registerLiveData

    private val _loginLiveData = MutableLiveData<DataState<User>>()
    val loginLiveData: LiveData<DataState<User>> = _loginLiveData

    fun register(user: User) {
        viewModelScope.launch {
            usersRepository.register(user).collectLatest {
                _registerLiveData.postValue(it)
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            usersRepository.login(email, password).collectLatest {
                _loginLiveData.postValue(it)
            }
        }
    }

    suspend fun getUser(): Flow<DataState<User>> {
        return usersRepository.getUser()
    }
}