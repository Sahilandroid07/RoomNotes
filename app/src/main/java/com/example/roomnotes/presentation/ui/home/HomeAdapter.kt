package com.example.roomnotes.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomnotes.data.local.db.entities.Note
import com.example.roomnotes.databinding.ViewHolderHomeBinding

class HomeAdapter(
    private val onItemClick: (note: Note) -> Unit
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(ViewHolderHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class HomeViewHolder(private val binding: ViewHolderHomeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(note: Note){
            binding.note = note
            binding.root.setOnClickListener {
                onItemClick(differ.currentList[adapterPosition])
            }
        }

    }
    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
           return oldItem.id == newItem.id
        }

    })


}