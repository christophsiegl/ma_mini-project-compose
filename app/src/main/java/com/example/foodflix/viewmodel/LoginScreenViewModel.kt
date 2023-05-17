package com.example.foodflix.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.auth0.android.Auth0
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.callback.Callback
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.result.Credentials
import android.util.Log
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.example.foodflix.R
import com.example.foodflix.User

// 1
class LoginScreenViewModel() : ViewModel() {


    private val TAG = "MainViewModel"  // 1
    private lateinit var account: Auth0  // 2
    private lateinit var context: Context  // 3

    private var credentialsManager: CredentialsManager? = null


    var user by mutableStateOf(User())

    // 2
    var appJustLaunched by mutableStateOf(true)
    var userIsAuthenticated by mutableStateOf(false)

    // 3
    fun login() {
        WebAuthProvider
            .login(account)
            .withScheme(context.getString(R.string.com_auth0_scheme))
            .start(context, object : Callback<Credentials, AuthenticationException> {

                override fun onFailure(error: AuthenticationException) {
                    // The user either pressed the “Cancel” button
                    // on the Universal Login screen or something
                    // unusual happened.
                    Log.e(TAG, "Error occurred in login(): $error")
                }

                override fun onSuccess(result: Credentials) {
                    val idToken = result.idToken
                    Log.d(TAG, "ID token: $idToken")
                    user = User(idToken)
                    userIsAuthenticated = true
                    appJustLaunched = false
                    credentialsManager?.saveCredentials(result)



                }

            })
    }

    // 4
    fun logout() {
        WebAuthProvider
            .logout(account)
            .withScheme(context.getString(R.string.com_auth0_scheme))
            .start(context, object : Callback<Void?, AuthenticationException> {

                override fun onFailure(error: AuthenticationException) {
                    // For some reason, logout failed.
                    Log.e(TAG, "Error occurred in logout(): $error")
                }

                override fun onSuccess(result: Void?) {
                    user = User()
                    credentialsManager?.clearCredentials()
                    // The user successfully logged out.
                    userIsAuthenticated = false


                }

            })
    }

    fun setContext(activityContext: Context) {
        context = activityContext
        account = Auth0(
            context.getString(R.string.com_auth0_client_id),
            context.getString(R.string.com_auth0_domain)
        )
        val authenticationClient = AuthenticationAPIClient(account)
        credentialsManager = CredentialsManager(authenticationClient, SharedPreferencesStorage(context))
        userIsAuthenticated = credentialsManager!!.hasValidCredentials()
    }

    suspend fun isUserLoggedIn(){
        if(userIsAuthenticated){
            user = User(credentialsManager!!.awaitCredentials().idToken)
        }
    }

}