package com.example.chatapp.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.chatapp.R
import com.example.chatapp.common.viewBinding
import com.example.chatapp.databinding.FragmentSendCodeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendCodeFragment : Fragment(R.layout.fragment_send_code) {

    private val binding by viewBinding(FragmentSendCodeBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
          
        }

    }


}