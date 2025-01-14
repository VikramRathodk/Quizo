package org.devvikram.quizo.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import org.devvikram.quizo.models.Users
import org.devvikram.quizo.utils.SharedPreference


class AppViewModel(
    private val firebase: Firebase,
    private val sharedPreference: SharedPreference
) : ViewModel() {

    private val _registrationState: MutableStateFlow<RegistrationState> = MutableStateFlow<RegistrationState>(RegistrationState.Initial)
    val registrationState: MutableStateFlow<RegistrationState> = _registrationState

    private val  _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Initial)
    val loginState: MutableStateFlow<LoginState> = _loginState



    private val firestore = firebase.firestore



    fun registerUser(userModel: Users) {
        _registrationState.value = RegistrationState.Loading
        val batch = firestore.batch()
        val userRef = firestore.collection("users").document()

        val userWithId = userModel.copy(userId = userRef.id)
        batch.set(userRef, userWithId)

        try {
            batch.commit().addOnSuccessListener {
                println("User has been saved with ID: ${userRef.id}")
                _registrationState.value = RegistrationState.Success("User has been saved with ID: ${userRef.id}")
            }.addOnFailureListener { e ->
                println("Failed to save user: ${e.message}")
                _registrationState.value = RegistrationState.Error("Failed to save user: ${e.message}")
                e.printStackTrace()
            }
        } catch (e: Exception) {
            println("Exception occurred: ${e.message}")
            _registrationState.value = RegistrationState.Error("Exception occurred: ${e.message}")
            e.printStackTrace()
        }
    }


    fun loginUser(email: String, password: String) {
        _loginState.value = LoginState.Loading

        firestore.collection("users")
            .whereEqualTo("email", email)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                        val user = document.toObject(Users::class.java)
                        println("User found: $user")

                        sharedPreference.saveUser(
                            username = user.name,
                            useremail = user.email,
                            userpassword = user.password
                        )
                        _loginState.value = LoginState.Success("Login successful for: ${user.name}")
                        return@addOnSuccessListener
                    }
                } else {
                    println("No user found with email: $email and password: $password")
                    _loginState.value = LoginState.Error("Invalid email or password.")
                }
            }
            .addOnFailureListener { e ->
                println("Error logging in with email: $email")
                _loginState.value = LoginState.Error("An error occurred during login. Please try again.")
                e.printStackTrace()
            }
    }
    fun logoutUser() {
        sharedPreference.clearUser()
        _loginState.value = LoginState.Initial
    }

    sealed class LoginState {
        data object Initial : LoginState()
        data object Loading : LoginState()
        data class Success(val message: String) : LoginState()
        data class Error(val message: String) : LoginState()
    }


    sealed class RegistrationState {
        object Initial : RegistrationState()
        object Loading : RegistrationState()
        data class Success(val message: String) : RegistrationState()
        data class Error(val message: String) : RegistrationState()


    }
}