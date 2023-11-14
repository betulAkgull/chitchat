package com.example.chatapp.ui.sendcode

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.common.gone
import com.example.chatapp.common.viewBinding
import com.example.chatapp.common.visible
import com.example.chatapp.databinding.FragmentSendCodeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class SendCodeFragment : Fragment(R.layout.fragment_send_code) {

    private val binding by viewBinding(FragmentSendCodeBinding::bind)

    private val viewModel by viewModels<SendCodeViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            observeData()

            ccp.setDefaultCountryUsingNameCode(Locale.getDefault().country)
            ccp.resetToDefaultCountry()
            ccp.registerCarrierNumberEditText(etPhoneNumber)


            btnSendCode.setOnClickListener {
                if (ccp.isValidFullNumber) {
                    val number = ccp.formattedFullNumber.replace(" ", "")
                    viewModel.sendOtp(number, false, requireActivity())
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please enter valid phone number",
                        Toast.LENGTH_SHORT
                    ).show()
                    etPhoneNumber.requestFocus()
                }

            }
        }


    }

    private fun observeData() = with(binding) {
        viewModel.sendCodeState.observe(viewLifecycleOwner) { state ->

            when (state) {

                SendCodeState.Loading -> {
                    lottieProgressBar.visible()
                }

                is SendCodeState.Data -> {
                    lottieProgressBar.gone()
                    Toast.makeText(requireContext(), "Code Sent!", Toast.LENGTH_SHORT).show()
                }

                is SendCodeState.AuthCredential -> {
                    lottieProgressBar.gone()
                    findNavController().navigate(SendCodeFragmentDirections.sendCodeToLogin(state.authCredential))
                }

                is SendCodeState.Error -> {
                    lottieProgressBar.gone()
                }


            }


        }
    }


}