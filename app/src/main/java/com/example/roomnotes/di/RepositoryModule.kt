package com.example.roomnotes.di

import com.example.roomnotes.data.local.repository.NotesRepository
import com.example.roomnotes.data.local.repository.NotesRepositoryImpl
import com.example.roomnotes.data.local.repository.UsersRepository
import com.example.roomnotes.data.local.repository.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository

    @Binds
    abstract fun bindNotesRepository(notesRepositoryImpl: NotesRepositoryImpl): NotesRepository
}