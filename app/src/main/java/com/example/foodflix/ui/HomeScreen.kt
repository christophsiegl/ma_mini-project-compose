package com.example.foodflix.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onToSearchScreenClicked: () -> Unit = {},
    onToLoginScreenClicked: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "HomeScreen", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onToSearchScreenClicked,
            modifier = modifier.widthIn(min = 250.dp)
        ) {
            Text("To Search Screen")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onToLoginScreenClicked,
            modifier = modifier.widthIn(min = 250.dp)
        ) {
            Text("To Login Screen")
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun SelectQuantityButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text(stringResource(labelResourceId))
    }
}

@Preview
@Composable
fun HomePreview(){
    HomeScreen()
}
