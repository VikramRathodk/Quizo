package org.devvikram.quizo.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.devvikram.quizo.presentation.HomeScreen
import org.devvikram.quizo.presentation.LoginScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.Login.route

    ) {
        composable(Destination.Home.route) {
            HomeScreen(
                navHostController = navController,
            )
        }
        composable(Destination.Login.route){
            LoginScreen(
                navController = navController,
            )
        }
    }
}