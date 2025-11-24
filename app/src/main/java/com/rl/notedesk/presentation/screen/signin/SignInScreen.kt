package com.rl.notedesk.presentation.screen.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rl.notedesk.R
import com.rl.notedesk.presentation.component.ForgotPasswordBottomSheet
import com.rl.notedesk.presentation.component.OutlinedInputField
import com.rl.notedesk.presentation.navigation.Screen
import com.rl.notedesk.presentation.state.AuthState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    val signInViewModel: SignInViewModel = hiltViewModel()
    val authState by signInViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            navController.navigate(
                route = Screen.HomeScreen.route
            ) {
                popUpTo( route = Screen.SignInScreen.route ) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showSheet = false
            },
            sheetState = sheetState
        ) {
            ForgotPasswordBottomSheet(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) { email ->
                signInViewModel.sendResetPasswordLink(email)
                showSheet = false
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.welcome_back),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        )

        Text(
            text = stringResource(id = R.string.email_address),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        )


        OutlinedInputField(
            value = email,
            onValueChange = {
                email = it
                emailError = email.isEmpty()
            },
            placeholder = stringResource(id = R.string.email_hint),
            isError = emailError,
            errorMessage = if (emailError) "Email can't be empty" else null,
            keyboardOptions = KeyboardOptions (
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Text(
            text = stringResource(id = R.string.password),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        )

        OutlinedInputField(
            value = password,
            onValueChange = {
                password = it
                passwordError = password.isEmpty()
            },
            placeholder = stringResource(id = R.string.password_hint),
            isError = passwordError,
            errorMessage = if (passwordError) "Password can't be empty" else null,
            keyboardOptions = KeyboardOptions (
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            isVisualTransformation = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Text(
            text = stringResource(id = R.string.forgot_password),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.End)
                .clickable {
                    showSheet = true
                }
        )

        if (authState is AuthState.Error) {
            Text(
                text = (authState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )
        }

        Button(
            onClick = {
                signInViewModel.signIn(email, password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = email.isNotEmpty() && password.isNotEmpty()
        ) {
            if (authState is AuthState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = stringResource(id = R.string.sign_in),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.sign_up_message),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            Text(
                text = stringResource(id = R.string.sign_up),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        navController.navigate(route = Screen.SignUpScreen.route)
                    }
            )
        }
    }
}