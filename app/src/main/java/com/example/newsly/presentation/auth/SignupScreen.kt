package com.example.newsly.presentation.auth


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsly.presentation.navigation.Routes

@Composable
fun SignupScreen(
    navController: NavController,
    vm: AuthViewModel = hiltViewModel()
) {
    val state by vm.signup.collectAsState()

    fun goHome() {
        navController.navigate(Routes.Home.route) {
            popUpTo(Routes.Signup.route) { inclusive = true }
        }
    }

    Scaffold { pad ->
        Surface(modifier = Modifier.padding(pad)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Create your account", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(24.dp))

                OutlinedTextField(
                    value = state.name,
                    onValueChange = vm::onSignupNameChanged,
                    label = { Text("Full name (optional)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = state.email,
                    onValueChange = vm::onSignupEmailChanged,
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = state.password,
                    onValueChange = vm::onSignupPasswordChanged,
                    label = { Text("Password (min 6 chars)") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                if (state.error != null) {
                    Text(text = state.error!!, color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(8.dp))
                }

                Button(
                    onClick = { vm.submitSignup(::goHome) },
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (state.isLoading) CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
                    else Text("Create account")
                }

                Spacer(Modifier.height(16.dp))
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Already have an account? Sign in")
                }
            }
        }
    }
}