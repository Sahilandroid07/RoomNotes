package com.example.roomnotes.presentation.ui.auth

import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.roomnotes.data.local.db.entities.User
import com.example.roomnotes.databinding.FragmentRegisterBinding
import com.example.roomnotes.domain.DataState
import com.example.roomnotes.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val authViewModel by viewModels<AuthViewModel>()
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun setup() {
        observeLiveData()
        setOnClickListener()
    }


    private fun setOnClickListener() {
        binding.setListener {
            when (it) {
                binding.btnSignUp -> {
                    val name = binding.etName.text.toString().trim()
                    val email = binding.etEmail.text.toString().trim()
                    val password = binding.etPassword.text.toString().trim()
                    val user = User(fullName = name, email = email, password = password)
                    if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        authViewModel.register(user)
                    }
                    else {
                        if (name.isEmpty() && email.isEmpty() && password.isEmpty()) {
                            Toast.makeText(
                                requireContext(),
                                "Please fill all fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (name.isEmpty()) {
                            Toast.makeText(
                                requireContext(),
                                "Please enter name",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (email.isEmpty()) {
                            Toast.makeText(
                                requireContext(),
                                "Please enter email",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (password.isEmpty()) {
                            Toast.makeText(
                                requireContext(),
                                "Please enter password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
        }
    }

    private fun observeLiveData() {
        authViewModel.registerLiveData.observe(viewLifecycleOwner) {
            binding.isProgress = it is DataState.Loading
            when (it) {
                is DataState.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }

                DataState.Loading -> {
                }

                is DataState.Success -> {
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
                }
            }
        }
    }


}