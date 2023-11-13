package com.example.chatapp.data.repository

import com.example.chatapp.common.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UserRepo {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun signInWithPhone(credential: PhoneAuthCredential): Resource<AuthResult> {
        return try {
            val result = firebaseAuth.signInWithCredential(credential).await()
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }

    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}


interface UserRepo {
    val currentUser: FirebaseUser?
    suspend fun signInWithPhone(credential: PhoneAuthCredential): Resource<AuthResult>
    fun logout()
}