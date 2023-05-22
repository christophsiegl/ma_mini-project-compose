package com.example.foodflix

import android.os.Bundle
import android.view.View
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
            // Hide the status bar.
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            actionBar?.hide()

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