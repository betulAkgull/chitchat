package com.example.chatapp.ui.login

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.common.Resource
import com.example.chatapp.data.repository.UserRepo
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: UserRepo
) : ViewModel() {

    private var _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState>
        get() = _loginState

    lateinit var mResendToken: PhoneAuthProvider.ForceResendingToken
    var mVerificationId: String = ""


    fun sendOtp(phone: String, resend: Boolean, activity: Activity) {
        if (resend) resendVerificationCode(phone, activity)
        else sendVerificationCode(phone, activity)
    }

    fun signInWithPhone(credential: PhoneAuthCredential) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            val result = repo.signInWithPhone(credential)

            if (result is Resource.Success) {
                _loginState.value = LoginState.AuthData(result.data)
            } else if (result is Resource.Error) {
                _loginState.value = LoginState.Error(result.throwable)
            }
        }
    }

    fun sendVerificationCode(phone: String, activity: Activity) {
        _loginState.value = LoginState.Loading
        val options =
            PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance()).setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(mCallbacks)
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    fun resendVerificationCode(phone: String, activity: Activity) {
        _loginState.value = LoginState.Loading
        val options =
            PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance()).setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(mCallbacks)
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(authCredential: PhoneAuthCredential) {
            _loginState.value = LoginState.AuthCredential(authCredential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            _loginState.value = LoginState.Error(e.fillInStackTrace())
        }

        override fun onCodeSent(
            verificationId: String,
            forceResendingToken: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, forceResendingToken)
            mResendToken = forceResendingToken
            mVerificationId = verificationId
            _loginState.value = LoginState.Data(true)
        }


    }

}

sealed interface LoginState {
    object Loading : LoginState
    data class AuthData(val authResult: AuthResult) : LoginState
    data class Data(val codeSent: Boolean) : LoginState
    data class AuthCredential(val authCredential: PhoneAuthCredential) : LoginState
    data class Error(val throwable: Throwable) : LoginState
}