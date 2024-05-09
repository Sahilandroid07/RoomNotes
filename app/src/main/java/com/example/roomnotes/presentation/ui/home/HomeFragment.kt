package com.example.roomnotes.presentation.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.icu.lang.UCharacter.VerticalOrientation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Orientation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.roomnotes.data.local.db.entities.Note
import com.example.roomnotes.databinding.FragmentHomeBinding
import com.example.roomnotes.domain.DataState
import com.example.roomnotes.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.prefs.Preferences

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var prefs: SharedPreferences
    private val notesViewModel by viewModels<NotesViewModel>()
    private val homeAdapter by lazy { HomeAdapter(this::onAdapterItemClick) }
    private val IS_GRID = "IS_GRID"

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun setup() {
        observeLiveData()
        onClickListener()
        binding.recyclerview.adapter = homeAdapter
        notesViewModel.getAllNotes()
        prefs = requireContext().getSharedPreferences("com.example.roomnotes", Context.MODE_PRIVATE)
        val isGrid = prefs.getBoolean(IS_GRID, false)
        binding.recyclerview.layoutManager = if(isGrid) GridLayoutManager(requireContext(), 2)
        else LinearLayoutManager(requireContext())
        binding.isGrid = isGrid
    }

    private fun onClickListener() {
        binding.setListener {
            when (it) {
                binding.btnAdd -> {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToNotesAddEditFragment(null))
                }
                binding.ivChangeType -> {
                    binding.isGrid = !binding.isGrid
                    binding.recyclerview.layoutManager = if (binding.isGrid) {
                        prefs.edit().putBoolean(IS_GRID, true).commit()
                        GridLayoutManager(requireContext(), 2)
                    }
                    else {
                        prefs.edit().putBoolean(IS_GRID, false).commit()
                        LinearLayoutManager(requireContext())
                    }
                }
            }
        }

    }

    private fun observeLiveData() {
        notesViewModel.allNotesLiveData.observe(viewLifecycleOwner) {
            binding.isProgress = it is DataState.Loading
            when (it) {
                is DataState.Error -> {}
                DataState.Loading -> {}
                is DataState.Success -> {
                    homeAdapter.differ.submitList(it.data)
                    binding.isListEmpty = it.data.isEmpty()
                }

            }
        }
    }
    private fun onAdapterItemClick(note: Note) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToNotesAddEditFragment(
                note
            )
        )
    }
}


