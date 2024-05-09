package com.example.roomnotes.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomnotes.data.local.db.dao.NotesDao
import com.example.roomnotes.data.local.db.dao.UsersDao
import com.example.roomnotes.data.local.db.entities.Note
import com.example.roomnotes.data.local.db.entities.User

@Database(
    entities = [
        User::class,
        Note::class
    ],
    version = 3,
    exportSchema = false
)
abstract class NotesDatabase : RoomDatabase() {

    abstract val usersDao: UsersDao
    abstract val notesDao: NotesDao
}