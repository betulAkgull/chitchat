package com.example.chatapp.ui.sendcode

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.data.PhoneAuthCredentialArgs
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class SendCodeViewModel : ViewModel() {

    private var _sendCodeState = MutableLiveData<SendCodeState>()
    val sendCodeState: LiveData<SendCodeState>
        get() = _sendCodeState

    lateinit var mResendToken: PhoneAuthProvider.ForceResendingToken
    var mVerificationId: String = ""


    fun sendOtp(phone: String, resend: Boolean, activity: Activity) {
        if (resend) resendVerificationCode(phone, activity)
        else sendVerificationCode(phone, activity)
    }

    fun sendVerificationCode(phone: String, activity: Activity) {
        _sendCodeState.value = SendCodeState.Loading
        val options =
            PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance()).setPhoneNumber(phone)
                .setTimeout(5L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(mCallbacks)
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    fun resendVerificationCode(phone: String, activity: Activity) {
        val options =
            PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance()).setPhoneNumber(phone)
                .setTimeout(5L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(mCallbacks)
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(authCredential: PhoneAuthCredential) {
            _sendCodeState.value = SendCodeState.AuthCredential(PhoneAuthCredentialArgs(authCredential))
        }

        override fun onVerificationFailed(e: FirebaseException) {
            _sendCodeState.value = SendCodeState.Error(e.fillInStackTrace())
        }

        override fun onCodeSent(
            verificationId: String,
            forceResendingToken: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, forceResendingToken)
            mResendToken = forceResendingToken
            mVerificationId = verificationId
            _sendCodeState.value = SendCodeState.Data(verificationId)
        }


    }

}

sealed interface SendCodeState {
    object Loading : SendCodeState
    data class Data(val verificationId: String) : SendCodeState
    data class AuthCredential(val authCredential: PhoneAuthCredentialArgs) : SendCodeState
    data class Error(val throwable: Throwable) : SendCodeState
}