package com.example.chatapp.data

import android.os.Parcelable
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhoneAuthCredentialArgs(val phoneAuthCredential: PhoneAuthCredential) : Parcelable
