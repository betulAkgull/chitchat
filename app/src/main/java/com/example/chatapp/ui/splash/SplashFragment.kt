package com.example.chatapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.common.viewBinding
import com.example.chatapp.data.repository.AuthRepoImpl
import com.example.chatapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    @Inject
    lateinit var authRepoImpl: AuthRepoImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            Handler().postDelayed({
                if (authRepoImpl.currentUser != null) {
                    findNavController().navigate(SplashFragmentDirections.splashToHome())
                } else {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSendCodeFragment())
                }
            }, 4000)
        }
    }
}