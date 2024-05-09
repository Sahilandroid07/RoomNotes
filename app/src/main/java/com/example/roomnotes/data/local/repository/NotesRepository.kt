package com.example.roomnotes.data.local.repository

import com.example.roomnotes.data.local.db.entities.Note
import com.example.roomnotes.domain.DataState
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    
    suspend fun createNote(note: Note): Flow<DataState<Boolean>>
    
    suspend fun updateNote(note: Note): Flow<DataState<Boolean>>
    
    suspend fun deleteNote(note: Note): Flow<DataState<Boolean>>
    
    suspend fun getAllNotes(): Flow<DataState<List<Note>>>
}