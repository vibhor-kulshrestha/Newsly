package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.presentation.splash.SplashScreen
import com.example.newsly.presentation.auth.LoginScreen
import com.example.newsly.presentation.auth.SignupScreen
import com.example.newsly.presentation.home.HomeScreen
import com.example.newsly.presentation.navigation.Routes
import com.example.newsly.ui.theme.NewslyTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            NewslyTheme {
                NewslyAppNavigation()
            }
        }
    }
}

@Composable
fun NewslyAppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Routes.Splash.route) {
        composable(Routes.Splash.route) { SplashScreen(navController) }
        composable(Routes.Login.route) { LoginScreen(navController) }
        composable(Routes.Signup.route) { SignupScreen(navController) }
        composable(Routes.Home.route) { HomeScreen() }
    }
}

