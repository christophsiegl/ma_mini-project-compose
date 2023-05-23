package com.example.foodflix.ui

import android.graphics.Paint
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

    // Trigger the worker when recipeIds value changes
    LaunchedEffect(recipeIds) {
        recipeListViewModel.deleteAllMeals()
        if (email?.isNotEmpty() == true) {
            viewModel.getFavouriteRecipeIdsFromDatabase(email!!)
        }

        if (recipeIds?.isNotEmpty() == true) {
            viewModel.createWorkManagerTaskMealDetail(RecipeFetchWorker.RequestType.GET_RECIPE_DETAIL_SIMPLE, recipeIds)
        }
    }

    Column (modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center)
        .padding(top = 10.dp)
    ){
        Spacer(modifier = Modifier.height(20.dp))
        if (email?.isNotEmpty() == true) {
            TitleHome(text = "Saved Meals")
        } else {
            TitleHome(text = "Foodflix")
        }
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center)
        .padding(top = 10.dp)
    )
    {
        if (email?.isNotEmpty() == true) {
            Column {
                Spacer(modifier = Modifier.height(65.dp))
                recipeList?.let { BrowseContentHome(recipes = it, navController) }
            }
        }
        else{
            Spacer(modifier = Modifier.height(80.dp))
            TitleHomeSmall(text = "Please sign in to see your saved meals!")
        }
    }
}

@Composable
fun BrowseContentHome(recipes: List<Meal>, navController: NavController) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 180.dp),
        contentPadding = PaddingValues(
            start = 2.dp,
            end = 2.dp,
            top = 6.dp,
            bottom = 6.dp
        ),
        content = {
            items(recipes.size) {
                RecipeListItemHome(meal = recipes[it], navController)
            }
        }
    )
}

@Composable
fun TitleHome(  // 1
    text: String,
) {
    Text(  // 2
        text = text,
        style = TextStyle(
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,  // 3
        )
    )
}

@Composable
fun TitleHomeSmall(  // 1
    text: String,
) {
    Text(  // 2
        text = text,
        style = TextStyle(
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,  // 3
        )
    )
}

@Composable
fun RecipeListItemHome(meal: Meal, navController: NavController) {
    Row {
        Card(
            backgroundColor = OffBlackBlueHintDarker,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp, start = 8.dp, end = 8.dp)
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

@Preview
@Composable
fun HomePreview(){
    //HomeScreen()
}