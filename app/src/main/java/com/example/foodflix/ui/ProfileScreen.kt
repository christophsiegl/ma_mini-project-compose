package com.example.foodflix.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodflix.viewmodel.LoginScreenViewModel
import com.example.foodflix.viewmodel.LoginScreenViewModelFactory
import com.example.foodflix.viewmodel.ProfileScreenViewModel
import com.example.foodflix.viewmodel.ProfileScreenViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    profileScreenViewModel: ProfileScreenViewModel = viewModel(factory = ProfileScreenViewModelFactory(LocalContext.current))
){
    val email = navController.currentBackStackEntry?.arguments?.getString("email")


    val corutineScope = rememberCoroutineScope()


    var age = LaunchedEffect(Unit){
        corutineScope.launch { profileScreenViewModel.readAgeFromDatabase(email!!).toString() }
    }.toString()


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = age,
            onValueChange = { newText ->
                age = newText
            }
        )

        Button(
            onClick = {
                corutineScope.launch{callFunction(email!!, age.toInt(), profileScreenViewModel) }
                      },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Call Function NOW")
        }
    }
}

suspend fun callFunction(email: String, age: Int, profileScreenViewModel : ProfileScreenViewModel) {
    profileScreenViewModel.updateAgeInDatabase(age, email)
    println("Calling function with email: $email and age: $age")
}
