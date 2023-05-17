package com.example.foodflix.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun RecipeDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier
){
    val selectedMealId = navController.currentBackStackEntry?.arguments?.getString("mealId")

    Column() {
        Text(text = "Recipe Detail Screen")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = selectedMealId.toString())
        /*
        Button(
            onClick = onToLoginScreenButtonClicked,
            modifier = modifier.widthIn(min = 250.dp)
        ) {
            Text("To Login Screen")
        }*/
    }
}

@Preview
@Composable
fun recipeDetailScreen(){
    RecipeDetailScreen(rememberNavController())
}