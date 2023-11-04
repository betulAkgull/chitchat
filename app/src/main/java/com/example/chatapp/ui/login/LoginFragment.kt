package com.example.chatapp.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.chatapp.R
import com.example.chatapp.common.viewBinding
import com.example.chatapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

        }
    }
}