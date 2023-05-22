package com.example.foodflix.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.work.*
import coil.compose.rememberAsyncImagePainter
import com.example.foodflix.FoodflixScreen
import com.example.foodflix.R
import com.example.foodflix.model.Meal
import com.example.foodflix.model.MealDetail
import com.example.foodflix.ui.theme.OffBlackBlueHintDarker
import com.example.foodflix.utils.Constants.NOT_LOGGED_IN
import com.example.foodflix.viewmodel.*
import com.example.foodflix.workers.RecipeFetchWorker
import com.google.gson.Gson

@Composable
fun HomeScreen(
    sharedViewModel: SharedViewModel,
    navController: NavController,
    viewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModelFactory(LocalContext.current)),
    recipeListViewModel: RecipeListViewModel = viewModel(factory = RecipeListViewModelFactory(LocalContext.current)),
    modifier: Modifier = Modifier
) {
    val recipeIds by viewModel.favouriteRecipeIds.collectAsState()
    val email by sharedViewModel.userMail.collectAsState()
    val recipeList by recipeListViewModel.recipeList.observeAsState()

    LaunchedEffect(Unit) {
        if (email?.isNotEmpty() == true) {
            viewModel.getFavouriteRecipeIdsFromDatabase(email!!)
        }
    }

    Column {
        Text(text = "Meal Details:")
        if(recipeIds?.isNotEmpty() == true){
            viewModel.createWorkManagerTaskMealDetail(RecipeFetchWorker.RequestType.GET_RECIPE_DETAIL_SIMPLE, recipeIds)
        }

        // Debugging
        Text(text = "\n\n\nMeal IDs saved:")
        val tempMealId = recipeIds?.split(":")
        val tempMealIds = recipeIds?.toString()

        if (tempMealId != null) {
            tempMealId.drop(1)
        }
        if (tempMealId != null) {
            for (mealID in tempMealId) {
                Text(text = mealID.toString())
            }
        }
    }
}

@Preview
@Composable
fun HomePreview(){
    //HomeScreen()
}