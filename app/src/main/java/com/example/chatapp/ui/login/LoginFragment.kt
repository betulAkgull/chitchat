package com.example.chatapp.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.chatapp.R
import com.example.chatapp.common.gone
import com.example.chatapp.common.viewBinding
import com.example.chatapp.common.visible
import com.example.chatapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.PhoneAuthProvider
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

            viewModel.sendOtp(args.phoneNumber, false, requireActivity())

            btnSendCode.setOnClickListener {
                val enteredOtp = etPhoneNumber.text.toString().trim()
                val credential =
                    PhoneAuthProvider.getCredential(viewModel.mVerificationId, enteredOtp)
                viewModel.signInWithPhone(credential)
            }

            tvResendCode.setOnClickListener {
                viewModel.sendOtp(args.phoneNumber, true, requireActivity())
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

                is LoginState.AuthCredential -> {
                    lottieProgressBar.gone()
                    viewModel.signInWithPhone(state.authCredential)
                }

                is LoginState.Data -> {
                    lottieProgressBar.gone()
                    if (state.codeSent) {
                        Toast.makeText(requireContext(), "Code Sent!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed at sending code",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is LoginState.Error -> {
                    lottieProgressBar.gone()
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }
    }
}