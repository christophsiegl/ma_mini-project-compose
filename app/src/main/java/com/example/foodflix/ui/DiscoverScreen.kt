package com.example.foodflix.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.foodflix.R
import com.example.foodflix.model.Meal
import com.example.foodflix.viewmodel.RecipeListViewModel
import com.example.foodflix.viewmodel.RecipeListViewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeListViewModel = viewModel(factory = RecipeListViewModelFactory(LocalContext.current))
){

    if (!isNetworkConnected(LocalContext.current)) {
        NoConnectionDialog()
    }
    val recipesFromViewModel by recipeViewModel.recipeList.observeAsState(emptyList())

    // already done in the init of the ViewModel, but could be useful later
    //LaunchedEffect(Unit) {
    //    recipeViewModel.createWorkManagerTask(RecipeFetchWorker.RequestType.GET_CANADIAN_RECIPES)
    //}

    Column {
        Spacer(modifier = Modifier.height(8.dp))
        BrowseContent(recipes = recipesFromViewModel)
    }
}


@Composable
fun BrowseContent(recipes: List<Meal>) {
    /*
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(items = recipes) {
            RecipeListItem(meal = it)
        }
    }*/
    /*
    LazyVerticalGrid(
        columns = StaggeredGridCells.Fixed(3),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(randomSizedPhotos) { photo ->
                AsyncImage(
                    model = photo,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
    */
/*
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(recipes.size) {
                Card(
                    backgroundColor = Color.Red,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    elevation = 8.dp,
                ) {
                    Text(
                        text = recipes[it].strMeal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        })*/

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(items = recipes) {
            RecipeListItem(meal = it)
        }
    }
}


@Composable
fun RecipeListItem(meal: Meal) {
    Row {
        Column {
            Image(
                painter = rememberAsyncImagePainter(meal.strMealThumb),
                contentDescription = stringResource(R.string.meal_picture_description),
                modifier = Modifier.size(226.dp)
            )
            Text(text = meal.strMeal, style = MaterialTheme.typography.h6)
            Text(text = "VIEW DETAIL", style = MaterialTheme.typography.caption)
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
