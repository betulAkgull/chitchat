package com.example.chatapp.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.chatapp.R
import com.example.chatapp.common.gone
import com.example.chatapp.common.viewBinding
import com.example.chatapp.common.visible
import com.example.chatapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    private val args by navArgs<LoginFragmentArgs>()

    private val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        with(binding) {

            btnSendCode.setOnClickListener {
                viewModel.signInWithPhone(args.authCredential.phoneAuthCredential)
            }
        }
    }

    private fun observeData() = with(binding) {
        viewModel.loginState.observe(viewLifecycleOwner) { state ->

            when (state) {

                LoginState.Loading -> {
                    lottieProgressBar.visible()
                }

                is LoginState.AuthData -> {
                    lottieProgressBar.gone()
                    findNavController().navigate(LoginFragmentDirections.loginToHome())
                }

                is LoginState.Error -> {
                    lottieProgressBar.gone()
                }

            }

        }
    }
}