package com.example.roomnotes.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomnotes.data.local.db.entities.Note
import com.example.roomnotes.data.local.db.entities.User
import com.example.roomnotes.data.local.repository.NotesRepository
import com.example.roomnotes.domain.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class   NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {
    private val _allNotesLiveData = MutableLiveData<DataState<List<Note>>>()
    val allNotesLiveData: LiveData<DataState<List<Note>>> = _allNotesLiveData

    private val _createNotesLiveData = MutableLiveData<DataState<Boolean>>()
    val createNotesLiveData: LiveData<DataState<Boolean>> = _createNotesLiveData

    private val _updateNotesLiveData = MutableLiveData<DataState<Boolean>>()
    val updateNotesLiveData: LiveData<DataState<Boolean>> = _updateNotesLiveData

    private val _deleteNotesLiveData = MutableLiveData<DataState<Boolean>>()
    val deleteNotesLiveData: LiveData<DataState<Boolean>> = _deleteNotesLiveData

    fun getAllNotes(){
        viewModelScope.launch {
            notesRepository.getAllNotes().collect {
                _allNotesLiveData.postValue(it)
            }
        }
    }

    fun createNotes(note: Note){
        viewModelScope.launch {
            notesRepository.createNote(note).collect {
                _createNotesLiveData.postValue(it)
            }
        }
    }
     fun updateNotes(note: Note){
         viewModelScope.launch {
             notesRepository.updateNote(note).collect {
                 _updateNotesLiveData.postValue(it)
             }
         }
    }
    fun deleteNotes(note: Note){
        viewModelScope.launch {
            notesRepository.deleteNote(note).collect {
                _deleteNotesLiveData.postValue(it)
            }
        }
    }


}