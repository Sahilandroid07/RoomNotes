package com.example.roomnotes.presentation

import android.view.LayoutInflater
import com.example.roomnotes.data.local.db.NotesDatabase
import com.example.roomnotes.databinding.ActivityMainBinding
import com.example.roomnotes.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getBinding(layoutInflater: LayoutInflater) = ActivityMainBinding.inflate(layoutInflater)
    override fun setup() {

    }
}