package com.example.foodflix.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onToHomeScreenButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Column() {
        Text(text = "Login Screen")
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onToHomeScreenButtonClicked,
            modifier = modifier.widthIn(min = 250.dp)
        ) {
            Text("To Home Screen")
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen()
}