package com.example.roomnotes.data.local.repository

import com.example.roomnotes.data.local.db.entities.User
import com.example.roomnotes.domain.DataState
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun register(user: User): Flow<DataState<User>>

    suspend fun login(email: String, password: String): Flow<DataState<User>>

    suspend fun getUser(): Flow<DataState<User>>
}