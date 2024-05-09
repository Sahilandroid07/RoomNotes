package com.example.roomnotes.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.roomnotes.data.local.db.entities.User

@Dao
interface UsersDao {
    @Insert
    suspend fun registerUser(user: User): Long

    @Query("SELECT * FROM User WHERE email =:email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM User")
    suspend fun getAllUsers(): List<User>

    @Query("UPDATE User SET isLoggedIn =:isLoggedIn WHERE email =:email")
    suspend fun updateUserLoginStatus(email: String, isLoggedIn: Boolean): Int
}