package com.example.foodflix.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.foodflix.FoodflixScreen
import com.example.foodflix.R
import com.example.foodflix.model.Meal
import com.example.foodflix.ui.theme.OffBlackBlueHint
import com.example.foodflix.ui.theme.OffBlackBlueHintDarker
import com.example.foodflix.viewmodel.RecipeListViewModel
import com.example.foodflix.viewmodel.RecipeListViewModelFactory
import com.example.foodflix.viewmodel.SharedViewModel
import com.example.foodflix.workers.RecipeFetchWorker

@Composable
fun DiscoverScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel = viewModel(),
    recipeViewModel: RecipeListViewModel = viewModel(factory = RecipeListViewModelFactory(LocalContext.current))
){

    if (!isNetworkConnected(LocalContext.current)) {
        NoConnectionDialog()
    }
    val recipesFromViewModel by recipeViewModel.recipeList.observeAsState(emptyList())
    val canSearch by sharedViewModel.canSearch.collectAsState()

    LaunchedEffect(Unit) {
        sharedViewModel.setCanSearch(true) // Set canSearch to true
        recipeViewModel.createWorkManagerTask("getCanadianRecipes")
    }

    // already done in the init of the ViewModel, but could be useful later
    //LaunchedEffect(Unit) {
    //    recipeViewModel.createWorkManagerTask(RecipeFetchWorker.RequestType.GET_CANADIAN_RECIPES)
    //}

    Column {
        Spacer(modifier = Modifier.height(8.dp))
        BrowseContent(recipes = recipesFromViewModel, navController)
    }
}
@Composable
fun BrowseContent(recipes: List<Meal>, navController: NavController) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 180.dp),
        contentPadding = PaddingValues(
            start = 2.dp,
            end = 2.dp,
            top = 6.dp,
            bottom = 6.dp
        ),
        content = {
            items(recipes.size) {
                RecipeListItem(meal = recipes[it], navController)
            }
        }
    )
}

@Composable
fun RecipeListItem(meal: Meal, navController: NavController) {
    Row {
        Card(
            backgroundColor = OffBlackBlueHintDarker,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp, start=8.dp, end=8.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate("${FoodflixScreen.RecipeDetail.name}/${meal.idMeal}")
                },
            elevation = 0.dp
        ) {
            Column(
            ) {
                Image(
                    painter = rememberAsyncImagePainter(meal.strMealThumb),
                    contentDescription = stringResource(R.string.meal_picture_description),
                    modifier = Modifier.size(188.dp)
                )
                Text( text = meal.strMeal, style = MaterialTheme.typography.h6, fontSize = 22.sp,
                    modifier = Modifier.height(55.dp))
            }
        }
    }
}

// Modified this: https://github.com/Foso/Jetpack-Compose-Playground/blob/master/app/src/main/java/de/jensklingenberg/jetpackcomposeplayground/mysamples/github/material/alertdialog/AlertDialogSample.kt
@Composable
fun NoConnectionDialog() {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(true)  }

            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
                        openDialog.value = false
                    },
                    title = {
                        Text(text = "No Connection")
                    },
                    text = {
                        Text("Please reconnect to the internet. ")
                    },
                    confirmButton = {
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("Ok")
                        }
                    }
                )
            }
        }

    }
}

private fun isNetworkConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
