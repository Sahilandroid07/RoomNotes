package com.example.roomnotes.presentation.ui.auth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.roomnotes.databinding.FragmentLoginBinding
import com.example.roomnotes.domain.DataState
import com.example.roomnotes.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val authViewModel by viewModels<AuthViewModel>()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun setup() {
        observeLiveData()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.setListener {
            when (it) {
                binding.btnLogin -> {
                    val email = binding.etEmail.text.toString().trim()
                    val password = binding.etPassword.text.toString().trim()
                    if (email.isNotEmpty() && password.isNotEmpty()){
                        authViewModel.login(email, password)

                    }
                    else{
                        if (email.isEmpty() && password.isEmpty()){
                            Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show()
                        }else if (email.isEmpty()){
                            Toast.makeText(requireContext(), "Please enter your email", Toast.LENGTH_SHORT).show()
                        }else if (password.isEmpty()){
                            Toast.makeText(requireContext(), "please enter password", Toast.LENGTH_SHORT).show()

                        }
                    }


                }
                binding.tvRegister ->{
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
                }
            }
        }
    }

    private fun observeLiveData() {
        authViewModel.loginLiveData.observe(viewLifecycleOwner) {
            binding.isProgress = it is DataState.Loading
            when(it){
                is DataState.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }
                DataState.Loading ->{}
                is DataState.Success ->{
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                }
            }
        }
    }
}