package com.example.foodflix.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodflix.viewmodel.LoginScreenViewModel
import com.example.foodflix.viewmodel.LoginScreenViewModelFactory
import com.example.foodflix.viewmodel.ProfileScreenViewModel
import com.example.foodflix.viewmodel.ProfileScreenViewModelFactory

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, loginScreenViewModel: LoginScreenViewModel = viewModel(factory = LoginScreenViewModelFactory()),profileScreenViewModel: ProfileScreenViewModel = viewModel(factory = ProfileScreenViewModelFactory())) {
    Column(Modifier.padding(16.dp)) {
        Text("The textfield has this text: " + 5)

       AgeTextField(
            age = profileScreenViewModel.readAgeFromDatabase(loginScreenViewModel.user.email),
           onUpdateClick = profileScreenViewModel::onAgeChange,
        )
    }
    Button(onClick = model::onUpdateClick) {
        Text("Update")
    }
}

@Composable
private fun AgeTextField(
    age: Int,
    onAgeChange: (Int) -> Unit,
) {
    var text by remember(age) {mutableStateOf(age.toString())}
    TextField(
        value = text,
        onValueChange =  {raw ->
            text = raw
            val parsed = raw.toIntOrNull() ?: 0
            onAgeChange(parsed)
        },
        label = { Text("Enter age") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
    )
}