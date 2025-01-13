package org.devvikram.quizo.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.devvikram.quizo.navigations.Destination
import org.devvikram.quizo.utils.SharedPreference


@Composable
fun HomeScreen(
    navHostController: NavHostController,
) {
    val sharedPreference = SharedPreference(LocalContext.current)

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.primary,
                title = {
                    Text(text = "Quizo")
                },
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = 4.dp,
                actions = {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clickable {
                                sharedPreference.clearUser()
                                navHostController.navigate(Destination.Login.route)
                            }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val email = sharedPreference.getEmail()
            Text(
                text = "Welcome $email to Home Screen",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                )
            )

        }

    }
}