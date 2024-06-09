package com.breens.orderfoodapp.feature_tasks.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.breens.orderfoodapp.feature_tasks.state.SignInScreenUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPassword(
    uiStateAccount: SignInScreenUiState,
    navController: NavController,
    setEmail: (String)-> Unit,
    resetPassword: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material3.OutlinedTextField(
            value = uiStateAccount.currentEmail,
            onValueChange = { email ->
                setEmail(email)
            },
            label = { androidx.compose.material3.Text(text = "Email") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black,
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                resetPassword()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset Password")
        }
    }
}
