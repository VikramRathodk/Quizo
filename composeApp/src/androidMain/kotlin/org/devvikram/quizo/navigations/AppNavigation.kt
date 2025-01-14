package org.devvikram.quizo.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.devvikram.quizo.presentation.AppViewModel
import org.devvikram.quizo.presentation.HomeScreen
import org.devvikram.quizo.presentation.LoginScreen
import org.devvikram.quizo.presentation.RegistrationScreen

@Composable
fun AppNavigation(
     appViewmodel: AppViewModel
){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.Login.route

    ) {
        composable(Destination.Home.route) {
            HomeScreen(
                navHostController = navController,
                appViewModel = appViewmodel
            )
        }
        composable(Destination.Login.route){
            LoginScreen(
                navController = navController,
                appViewModel = appViewmodel
            )
        }
        composable(Destination.Registration.route){

            RegistrationScreen(
                navHostController = navController,
                appViewModel = appViewmodel
            )
        }
    }
}