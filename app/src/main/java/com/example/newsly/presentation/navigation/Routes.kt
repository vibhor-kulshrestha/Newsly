package com.example.newsly.presentation.navigation

sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object Login : Routes("login")
    object Signup : Routes("signup")
    object Home : Routes("home")
    object ForgetPassword : Routes("forgetPassword")
    object Settings : Routes("settings")
}