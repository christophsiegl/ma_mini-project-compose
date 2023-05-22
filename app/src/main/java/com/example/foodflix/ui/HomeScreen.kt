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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.foodflix.FoodflixScreen
import com.example.foodflix.R
import com.example.foodflix.model.Meal
import com.example.foodflix.ui.theme.OffBlackBlueHintDarker
import com.example.foodflix.utils.Constants.NOT_LOGGED_IN
import com.example.foodflix.viewmodel.*

@Composable
fun HomeScreen(
    sharedViewModel: SharedViewModel,
    navController: NavController,
    viewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModelFactory(LocalContext.current)),
    modifier: Modifier = Modifier
){
    val recipeIds by viewModel.favouriteRecipeIds.collectAsState()
    val email by sharedViewModel.userMail.collectAsState()


    // TODO: is it possible to reuse the whole viewmodel from the other screen below ?!?!
    //val recipeViewModel: RecipeListViewModel = viewModel(factory = RecipeListViewModelFactory(LocalContext.current))

    LaunchedEffect(Unit) {
        if(email?.isNotEmpty() == true){
            viewModel.getFavouriteRecipeIdsFromDatabase(email!!)

        }
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if(recipeIds?.isNotEmpty() == true){
            val delim = ":"
            val list = recipeIds.split(delim)
            list.forEach { it -> Text(text = it) }
        }
    }

    Column {
        Spacer(modifier = Modifier.height(8.dp))
        // TODO: fix that to show the favourite recipes!
        //BrowseContent(recipes = recipesFromViewModel, navController)
    }
}

@Preview
@Composable
fun HomePreview(){
    //HomeScreen()
}
