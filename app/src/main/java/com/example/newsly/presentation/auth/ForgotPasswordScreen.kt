package com.example.newsly.presentation.auth


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    vm: ForgotPasswordViewModel = hiltViewModel()
) {
    val state by vm.forgotState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let {
            scope.launch {
                val result = snackbarHostState.showSnackbar(it, actionLabel = "Okay")
                when (result) {
                    SnackbarResult.ActionPerformed, SnackbarResult.Dismissed -> {
                        navController.popBackStack()
                    }
                }
                vm.clearSnackbar()
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Reset password", style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = state.email,
                        onValueChange = vm::onEmailChange,
                        label = { Text("Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))
                    if (state.error != null) {
                        Text(text = state.error!!, color = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.height(8.dp))
                    }

                    Button(
                        onClick = {
                            vm.submitResetPassword()
                        },
                        enabled = !state.isLoading,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (state.isLoading) CircularProgressIndicator(
                            Modifier.size(18.dp),
                            strokeWidth = 2.dp
                        )
                        else Text("Send reset email")
                    }

                    Spacer(Modifier.height(16.dp))
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("Back to sign in")
                    }
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}