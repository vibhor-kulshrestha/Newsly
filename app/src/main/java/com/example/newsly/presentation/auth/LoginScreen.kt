package com.example.newsly.presentation.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsly.presentation.navigation.Routes
import com.example.newsly.ui.theme.NewslyTheme

@Composable
fun LoginScreen(
    navController: NavController,
    vm: AuthViewModel = hiltViewModel()
) {
    val state = vm.login.collectAsState()
    fun goHome() {
        navController.navigate(Routes.Home.route) {
            popUpTo(Routes.Login.route) { inclusive = true }
        }
    }

    Scaffold { pad->
        Surface(modifier = Modifier.padding(pad)) {
            Box(Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Welcome to Newsly", style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(24.dp))

                    OutlinedTextField(
                        value = state.value.email,
                        onValueChange = vm::onLoginEmailChanged,
                        label = { Text("Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = state.value.password,
                        onValueChange = vm::onLoginPasswordChanged,
                        label = { Text("Password") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    if (state.value.error != null) {
                        Text(text = state.value.error!!, color = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.height(8.dp))
                    }

                    Button(
                        onClick = { vm.submitLogin(::goHome) },
                        enabled = !state.value.isLoading,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (state.value.isLoading) CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
                        else Text("Sign in")
                    }

                    Spacer(Modifier.height(16.dp))
                    TextButton(onClick = { navController.navigate(Routes.Signup.route) }) {
                        Text("No account? Create one")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    NewslyTheme {
        LoginScreen(NavController(LocalContext.current))
    }
}