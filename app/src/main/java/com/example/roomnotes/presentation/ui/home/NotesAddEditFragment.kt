package com.example.roomnotes.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomnotes.data.local.db.entities.Note
import com.example.roomnotes.databinding.FragmentNotesAddEditBinding
import com.example.roomnotes.domain.DataState
import com.example.roomnotes.presentation.base.BaseFragment
import com.example.roomnotes.presentation.ui.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesAddEditFragment : BaseFragment<FragmentNotesAddEditBinding>() {

    private val args by navArgs<NotesAddEditFragmentArgs>()
    private val notesViewModel by viewModels<NotesViewModel>()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentNotesAddEditBinding {
        return FragmentNotesAddEditBinding.inflate(inflater, container, false)
    }

    override fun setup() {
        binding.note = args.note
        observeLiveData()
        setOnClickListener()
    }


    private fun setOnClickListener() {
        binding.setListener {
            when (it) {
                binding.btnSubmit -> buttonSubmitClick()
                binding.btnDelete -> notesViewModel.deleteNotes(args.note ?: Note(-1, "", ""))
            }
        }
    }

    private fun buttonSubmitClick() {
        val title = binding.etTitle.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()
        val note = Note(title = title, description = description)
        if (args.note != null){
            notesViewModel.updateNotes(args.note?.copy(title = title, description = description) ?: note)
        }else{
            notesViewModel.createNotes(note)
        }
    }

    private fun observeLiveData() {
        notesViewModel.createNotesLiveData.observe(viewLifecycleOwner) {
            binding.isProgress = it is DataState.Loading
            when (it) {
                is DataState.Error -> showToast("Notes were not created")
                DataState.Loading -> {}
                is DataState.Success -> findNavController().popBackStack()
            }
        }
        notesViewModel.updateNotesLiveData.observe(viewLifecycleOwner){
            binding.isProgress = it is DataState.Loading
            when (it){
                is DataState.Error -> showToast("Notes were not update")
                DataState.Loading -> {}
                is DataState.Success ->{
                    findNavController().popBackStack()
                    showToast("Notes has been updated successfully")
                }
            }
        }

        notesViewModel.deleteNotesLiveData.observe(viewLifecycleOwner){
            binding.isProgress = it is DataState.Loading
            when(it){
                is DataState.Error -> {}
                DataState.Loading -> {}
                is DataState.Success -> {
                    findNavController().popBackStack()
                    showToast("Notes has been deleted successfully")
                }
            }
        }
    }
}