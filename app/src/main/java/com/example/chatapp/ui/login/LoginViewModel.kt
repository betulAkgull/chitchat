package com.example.chatapp.ui.login

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class LoginViewModel : ViewModel() {

    private var _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState>
        get() = _loginState

}

sealed interface LoginState {
    object Loading : LoginState
    data class Data(val phone: String) : LoginState
    data class Error(val throwable: Throwable) : LoginState
}