package com.example.foodflix.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeDetailScreen(
    onToLoginScreenButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
){

    Column() {
        Text(text = "Recipe Screen")
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onToLoginScreenButtonClicked,
            modifier = modifier.widthIn(min = 250.dp)
        ) {
            Text("To Login Screen")
        }
    }
}