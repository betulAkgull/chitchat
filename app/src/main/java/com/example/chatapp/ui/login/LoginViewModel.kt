package com.example.chatapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.common.Resource
import com.example.chatapp.data.repository.UserRepo
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: UserRepo
) : ViewModel() {


    private var _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState>
        get() = _loginState

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
}

sealed interface LoginState {
    object Loading : LoginState
    data class AuthData(val authResult: AuthResult) : LoginState
    data class Error(val throwable: Throwable) : LoginState
}