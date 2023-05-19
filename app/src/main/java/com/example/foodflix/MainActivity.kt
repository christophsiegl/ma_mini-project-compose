package com.example.foodflix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.example.foodflix.ui.theme.FoodflixTheme
import com.example.foodflix.viewmodel.SharedViewModel
import com.example.foodflix.viewmodel.SharedViewModelFactory

class MainActivity : ComponentActivity() {
    private val sharedViewModel by lazy { ViewModelProvider(this, SharedViewModelFactory(this)).get(SharedViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodflixTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FoodflixApp(sharedViewModel = sharedViewModel)
                }
            }
        }
    }
}