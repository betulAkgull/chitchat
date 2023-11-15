package com.example.chatapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.common.viewBinding
import com.example.chatapp.data.repository.AuthRepoImpl
import com.example.chatapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    @Inject
    lateinit var authRepoImpl: AuthRepoImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            btnLogout.setOnClickListener {
                authRepoImpl.logout()
                findNavController().navigate(HomeFragmentDirections.homeToSplash())
            }
        }
    }
}