package com.example.foodflix

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodflix.database.RecipeDatabase
import com.example.foodflix.repository.RecipeRepository
import com.example.foodflix.repository.RecipeRepositorySingleton
import com.example.foodflix.ui.HomeScreen
import com.example.foodflix.ui.LoginScreen
import com.example.foodflix.ui.SearchScreen
import com.example.foodflix.ui.theme.BrowseContent
import com.example.foodflix.ui.theme.BrowseScreen

enum class FoodflixScreen() {
    Login,
    Profile,
    Home,
    Recipe,
    Search,
    ThingsAtHome,
    Browse
}

val quantityOptions = listOf(
    Pair(1, 2),
    Pair(2, 6),
    Pair(3, 12)
)

@Composable
fun FoodflixAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back button"
                    )
                }
            }
        }
    )
}

@Composable
fun FoodflixApp(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    // TODO: Get current back stack entry
    // TODO: Get the name of the current screen

    val recipeRepository = RecipeRepositorySingleton.getInstance(
        RecipeDatabase.getDatabase(LocalContext.current))

    Scaffold(


        topBar = {
            FoodflixAppBar(
                canNavigateBack = false,
                navigateUp = { /* TODO: implement back navigation */ }
            )
        }


    ) { innerPadding ->
        //val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = FoodflixScreen.Home.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = FoodflixScreen.Home.name) {
                HomeScreen(
                    quantityOptions = quantityOptions, //can be used to pass arguments!!
                    onToSearchScreenClicked = {
                        navController.navigate(FoodflixScreen.Search.name)
                    },
                    onToLoginScreenClicked = {
                        navController.navigate(FoodflixScreen.Login.name)
                    },
                    onToBrowserScreenClicked = {
                        navController.navigate(FoodflixScreen.Browse.name)
                    }
                )
            }
            composable(route = FoodflixScreen.Login.name) {
                LoginScreen(
                    onToHomeScreenButtonClicked = {
                        navController.navigate(FoodflixScreen.Home.name)
                    }
                )
            }
            composable(route = FoodflixScreen.Search.name) {
                SearchScreen(
                    onToLoginScreenButtonClicked = {
                        navController.navigate(FoodflixScreen.Login.name)
                    }
                )
            }
            composable(route = FoodflixScreen.Browse.name) {
                BrowseScreen(
                    recipeRepository = recipeRepository
                )
            }
        }
    }
}