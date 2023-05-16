package com.example.foodflix.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.foodflix.R
import com.example.foodflix.model.Meal
import com.example.foodflix.viewmodel.RecipeListViewModel
import com.example.foodflix.viewmodel.RecipeListViewModelFactory

@Composable
fun DiscoverScreen(
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