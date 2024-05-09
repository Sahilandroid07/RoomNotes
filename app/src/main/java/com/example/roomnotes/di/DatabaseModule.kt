package com.example.roomnotes.di

import android.content.Context
import androidx.room.Room
import com.example.roomnotes.data.local.db.NotesDatabase
import com.example.roomnotes.data.local.db.dao.NotesDao
import com.example.roomnotes.data.local.db.dao.UsersDao
import com.example.roomnotes.data.local.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): NotesDatabase {
        return Room.databaseBuilder(
            appContext,
            NotesDatabase::class.java,
            "notes.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideUsersDao(notesDatabase: NotesDatabase): UsersDao {
        return notesDatabase.usersDao
    }

    @Singleton
    @Provides
    fun provideNotesDao(notesDatabase: NotesDatabase): NotesDao {
        return notesDatabase.notesDao
    }

}