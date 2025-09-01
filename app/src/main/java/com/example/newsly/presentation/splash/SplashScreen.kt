package com.example.newsapp.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsapp.R

@Composable
fun SplashScreen(navController: NavController, viewModel: SplashViewModel = hiltViewModel()) {
    val state by viewModel.splashState.collectAsState()
    LaunchedEffect(state) {
        when (state) {
            is SplashState.NavigateToLogin -> navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
            is SplashState.NavigateToHome -> navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
            else -> Unit
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = "Splash Logo",
            modifier = Modifier.size(150.dp)
        )
    }
}