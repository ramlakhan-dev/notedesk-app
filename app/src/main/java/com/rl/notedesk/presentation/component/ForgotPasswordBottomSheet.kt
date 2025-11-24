package com.rl.notedesk.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rl.notedesk.R
import com.rl.notedesk.presentation.state.AuthState

@Composable
fun ForgotPasswordBottomSheet(
    modifier: Modifier = Modifier,
    onSend: (String) -> Unit
) {

    var email by rememberSaveable { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {

        Text(
            text = stringResource(id = R.string.reset_password),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
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
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Button(
            onClick = {
                onSend(email)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = email.isNotEmpty()
        ) {
            Text(
                text = stringResource(id = R.string.send),
                fontWeight = FontWeight.Bold
            )
        }
    }

}