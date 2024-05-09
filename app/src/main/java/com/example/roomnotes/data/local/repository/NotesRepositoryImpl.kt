package com.example.roomnotes.data.local.repository

import com.example.roomnotes.data.local.db.dao.NotesDao
import com.example.roomnotes.data.local.db.entities.Note
import com.example.roomnotes.domain.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val notesDao: NotesDao
) : NotesRepository {

    override suspend fun createNote(note: Note): Flow<DataState<Boolean>> {
        return flow {
            emit(DataState.Loading)
            delay(1000)
            val created = notesDao.createNote(note)
            if (created > 0) {
                emit(DataState.Success(true))
            }else {
                emit(DataState.Error("Unable to create note"))
            }
        }
    }

    override suspend fun updateNote(note: Note): Flow<DataState<Boolean>> {
        return flow {
            emit(DataState.Loading)
            delay(1000)
            val updated = notesDao.updateNote(note)
            if (updated > 0) {
                emit(DataState.Success(true))
            }else {
                emit(DataState.Error("Unable to update note"))
            }
        }
    }

    override suspend fun deleteNote(note: Note): Flow<DataState<Boolean>> {
        return flow {
            emit(DataState.Loading)
            delay(1000)
            val deleted = notesDao.deleteNote(note)
            if (deleted > 0) {
                emit(DataState.Success(true))
            }else {
                emit(DataState.Error("Unable to delete note"))
            }
        }
    }

    override suspend fun getAllNotes(): Flow<DataState<List<Note>>> {
        return flow {
            emit(DataState.Loading)
            delay(1000)
            val notes = notesDao.getAllNotes()
            if (notes.isNotEmpty()) {
                emit(DataState.Success(notes))
            }else {
                emit(DataState.Success(emptyList()))
            }
        }
    }
}