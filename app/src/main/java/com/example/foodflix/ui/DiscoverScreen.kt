package com.example.foodflix.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.foodflix.FoodflixScreen
import com.example.foodflix.R
import com.example.foodflix.model.Meal
import com.example.foodflix.viewmodel.RecipeListViewModel
import com.example.foodflix.viewmodel.RecipeListViewModelFactory

@Composable
fun DiscoverScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeListViewModel = viewModel(factory = RecipeListViewModelFactory(LocalContext.current))
){
    val recipesFromViewModel by recipeViewModel.recipeList.observeAsState(emptyList())

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
            top = 2.dp,
            end = 2.dp,
            bottom = 2.dp
        ),
        content = {
            items(recipes.size) {
                RecipeListItem(meal = recipes[it], navController)
            }
        })
}

@Composable
fun RecipeListItem(meal: Meal, navController: NavController) {
    Row {
        Card(
            backgroundColor = Color.Gray,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .clickable() {
                    navController.navigate("${FoodflixScreen.RecipeDetail.name}/${meal.idMeal}")
                },
            elevation = 0.dp
        ) {
            Column {
                Image(
                    painter = rememberAsyncImagePainter(meal.strMealThumb),
                    contentDescription = stringResource(R.string.meal_picture_description),
                    modifier = Modifier.size(226.dp)
                )
                Text(text = meal.strMeal, style = MaterialTheme.typography.h6, fontSize = 18.sp)
                Text(text = "VIEW DETAIL", style = MaterialTheme.typography.caption)
            }
        }
    }
}