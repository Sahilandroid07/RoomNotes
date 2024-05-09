package com.example.roomnotes.data.local.repository

import com.example.roomnotes.data.local.db.dao.UsersDao
import com.example.roomnotes.data.local.db.entities.User
import com.example.roomnotes.domain.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersDao: UsersDao
) : UsersRepository {

    override suspend fun register(user: User): Flow<DataState<User>> {
        return flow {
            emit(DataState.Loading)
            delay(1000)
            val foundUser = usersDao.getUserByEmail(user.email)
            if (foundUser == null) {
                val create = usersDao.registerUser(user)
                if (create > 0) {
                    emit(DataState.Success(user))
                }else {
                    emit(DataState.Error("Unable to create user"))
                }
            }else {
                emit(DataState.Error("This email is already registered"))
            }
        }
    }

    override suspend fun login(email: String, password: String): Flow<DataState<User>> {
        return flow {
            emit(DataState.Loading)
            delay(1000)
            val user = usersDao.getUserByEmail(email)
            if (user != null) {
                if (user.email.equals(email, true) && user.password.equals(password, true)) {
                    usersDao.updateUserLoginStatus(email, true)
                    emit(DataState.Success(user))
                }else {
                    if (!user.email.equals(email, true)) {
                        emit(DataState.Error("Entered email is wrong"))
                    }else if (!user.password.equals(password, true)) {
                        emit(DataState.Error("Entered password is wrong"))
                    }else {
                        emit(DataState.Error("Entered email and password is wrong"))
                    }
                }
            }else {
                emit(DataState.Error("This email is not registered"))
            }
        }
    }

    override suspend fun getUser(): Flow<DataState<User>> {
        return flow {
            emit(DataState.Loading)
            val users = usersDao.getAllUsers()
            if (users.isNotEmpty()) emit(DataState.Success(users.first()))
            else emit(DataState.Error("No user found"))
        }
    }
}