package org.devvikram.quizo.presentation

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.devvikram.quizo.R
import org.devvikram.quizo.navigations.Destination
import org.devvikram.quizo.utils.SharedPreference


@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current

    val sharedPreference = SharedPreference(context)
    val isLoggedIn = sharedPreference.isUserLoggedIn()

    if (isLoggedIn) {
        LaunchedEffect(Unit) {
            navController.navigate(Destination.Home.route) {
                popUpTo(Destination.Login.route) { inclusive = true }
            }
        }
        return
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val passwordFocusRequester = remember { FocusRequester() }
    val keyboardController = androidx.compose.ui.platform.LocalSoftwareKeyboardController.current

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
                text = "Sign in to continue",
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
                value = email,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    autoCorrect = false,
                    showKeyboardOnFocus = true,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    // focus to password
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
                .padding(top = 8.dp, bottom = 8.dp),
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

            LoadingButton(isLoading = false, buttonText = "Sign In", onClick = {
                isLoading = true
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
                sharedPreference.saveUser(
                    username = "", useremail = email, userpassword = password
                )
                navController.navigate(Destination.Home.route)
                isLoading = false
            })

        }
    }


}

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier, isLoading: Boolean, buttonText: String, onClick: () -> Unit
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(50.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(
            color = MaterialTheme.colorScheme.primary
        )
        .clickable(enabled = !isLoading) { onClick() }
        .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = buttonText,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

