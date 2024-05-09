package com.example.roomnotes.presentation.ui.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.roomnotes.databinding.FragmentSplashBinding
import com.example.roomnotes.domain.DataState
import com.example.roomnotes.presentation.base.BaseFragment
import com.example.roomnotes.presentation.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val authViewModel by viewModels<AuthViewModel>()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun setup() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(2000)
            authViewModel.getUser().collectLatest {
                when(it) {
                    is DataState.Error -> findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                    is DataState.Loading -> {}
                    is DataState.Success -> {
                        val user = it.data
                        if (user.isLoggedIn) {
                            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                        }else {
                            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                        }
                    }
                }
            }
        }
    }
}