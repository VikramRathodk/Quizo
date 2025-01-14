package org.devvikram.quizo.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import org.devvikram.quizo.R
import org.devvikram.quizo.models.Users
import org.devvikram.quizo.navigations.Destination

@Composable
fun RegistrationScreen(navHostController: NavHostController, appViewModel : AppViewModel) {

    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val registrationState = appViewModel.registrationState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(state = rememberScrollState())
                .padding(horizontal = 16.dp)
                .wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(width = 200.dp, height = 200.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop,
                painter = painterResource(
                    id = R.drawable.login_logo
                ),
                contentDescription = "App Logo"
            )

            Text(
                text = "Register a account!",
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.titleMedium
            )
            if (errorMessage.isNotBlank()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = name,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    emailFocusRequester.requestFocus()
                }),
                isError = errorMessage.isNotBlank(),
                onValueChange = { value ->
                    name = value
                },
                label = {
                    Text(text = "Enter your username")
                })


            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .focusRequester(emailFocusRequester),
                value = email,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    passwordFocusRequester.requestFocus()
                }),
                isError = errorMessage.isNotBlank(),
                onValueChange = { value ->
                    email = value
                },
                label = {
                    Text(text = "Enter your email")
                })


            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester)
                .padding(top = 4.dp, bottom = 8.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Done

                ),
                value = password,
                onValueChange = {
                    password = it
                },
                isError = errorMessage.isNotBlank(),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }),
                trailingIcon = {
                    val iconResId =
                        if (passwordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Image(
                            painter = painterResource(id = iconResId),
                            contentDescription = description
                        )
                    }
                },
                label = {
                    Text(text = "Enter your password")
                })
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navHostController.navigate(Destination.Login.route)
                    }
                    .padding(top = 4.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Already have an account?",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = "Login",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        navHostController.navigate(Destination.Login.route)
                    }
                )
            }
            LoadingButton(isLoading = isLoading, buttonText = "Register", onClick = {
                isLoading = true
                if (name.isBlank()) {
                    isLoading = false
                    errorMessage = "Name cannot be empty"
                    return@LoadingButton
                }
                if (email.isBlank()) {
                    isLoading = false
                    errorMessage = "Email cannot be empty"
                    return@LoadingButton
                }
                if (password.isBlank()) {
                    isLoading = false
                    errorMessage = "Password cannot be empty"
                    return@LoadingButton
                }
                val userModel = Users(
                    userId = "0",
                    name = name,
                    email = email,
                    password = password,
                    address = "",
                    phone ="",
                )
                appViewModel.registerUser(userModel)
            })
            Spacer(modifier = Modifier.height(24.dp))

            when (val state = registrationState.value) {
                is AppViewModel.RegistrationState.Initial -> {
                    // No action needed
                }
                is AppViewModel.RegistrationState.Loading -> {
                    isLoading = true
                    errorMessage = ""
                }
                is AppViewModel.RegistrationState.Success -> {
                    isLoading = false
                    navHostController.navigate(Destination.Login.route){
                        popUpTo(Destination.Registration.route){inclusive = true}
                        launchSingleTop = true
                    }
                }
                is AppViewModel.RegistrationState.Error -> {
                    isLoading = false
                    errorMessage = state.message ?: "An unknown error occurred"
                }
                else -> {
                    // No action needed
                }
            }



        }
    }
}