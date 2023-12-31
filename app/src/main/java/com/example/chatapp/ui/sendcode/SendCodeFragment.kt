package com.example.chatapp.ui.sendcode

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.common.viewBinding
import com.example.chatapp.databinding.FragmentSendCodeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class SendCodeFragment : Fragment(R.layout.fragment_send_code) {

    private val binding by viewBinding(FragmentSendCodeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            ccp.setDefaultCountryUsingNameCode(Locale.getDefault().country)
            ccp.resetToDefaultCountry()
            ccp.registerCarrierNumberEditText(etPhoneNumber)


            btnSendCode.setOnClickListener {
                if (ccp.isValidFullNumber) {
                    val number = ccp.formattedFullNumber.replace(" ", "")
                    findNavController().navigate(SendCodeFragmentDirections.sendCodeToLogin(number))
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


}