package com.example.roomnotes.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.roomnotes.data.local.db.entities.Note

@Dao
interface NotesDao {

    @Insert
    suspend fun createNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note): Int

    @Delete
    suspend fun deleteNote(note: Note): Int

    @Query("SELECT * FROM Note")
    suspend fun getAllNotes(): List<Note>
}