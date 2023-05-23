package com.example.foodflix.ui

import android.util.Log
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
import com.example.foodflix.model.UserData
import com.example.foodflix.utils.Constants.NOT_LOGGED_IN
import com.example.foodflix.viewmodel.*
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    sharedViewModel: SharedViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    profileScreenViewModel: ProfileScreenViewModel = viewModel(factory = ProfileScreenViewModelFactory(LocalContext.current))
){
    val email by sharedViewModel.userMail.collectAsState()
    val corutineScope = rememberCoroutineScope()

    var ageToPrint by remember { mutableStateOf("") }
    var ageList by remember { mutableStateOf(emptyList<UserData>()) }
    LaunchedEffect(key1 = email){
        if(email != NOT_LOGGED_IN) {
            corutineScope.launch {
                val ageFromDatabase = profileScreenViewModel.getAllUsers()
                ageList = ageFromDatabase
                Log.d("AgeList", "All ages from database: $ageList")

                // Iterate through the ageList
                for (age in ageList) {
                    if (age.email == email) {
                        ageToPrint = age.age.toString()
                    }
                    // Perform operations on each age
                    // Example: Log each age value
                    Log.d("EMAIL", "Age value: ${age.email}")
                    Log.d("Age", "Age value: ${age.age}")
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = email!!
        )

        TextField(
            value = ageToPrint,
            onValueChange = { newText ->
                ageToPrint = newText
            }
        )

        Button(
            onClick = {
                corutineScope.launch{callFunction(email!!, ageToPrint.toInt(), profileScreenViewModel) }
                      },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Save")
        }
    }
}

suspend fun callFunction(email: String, age: Int, profileScreenViewModel : ProfileScreenViewModel) {
    profileScreenViewModel.updateAgeInDatabase(age, email)
    println("Calling function with email: $email and age: $age")
}
