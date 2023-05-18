package com.example.foodflix.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodflix.model.MealDetail
import com.example.foodflix.viewmodel.RecipeDetailViewModel
import com.example.foodflix.viewmodel.RecipeDetailViewModelFactory
import com.example.foodflix.workers.RecipeFetchWorker
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun RecipeDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    recipeDetailViewModel: RecipeDetailViewModel = viewModel(factory = RecipeDetailViewModelFactory(
        LocalContext.current)
    )
){
    val recipeDetailFromViewModel by recipeDetailViewModel.mealDetails.observeAsState(emptyList())

    val selectedMealId = navController.currentBackStackEntry?.arguments?.getString("mealId")
    recipeDetailViewModel.createWorkManagerTask(RecipeFetchWorker.RequestType.GET_RECIPE_DETAIL ,selectedMealId!!)

    Column() {
        Text(text = "Recipe Detail Screen")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = selectedMealId.toString())
        Spacer(modifier = Modifier.height(8.dp))
        if(recipeDetailFromViewModel.isNotEmpty())
        {
            Text(text = recipeDetailFromViewModel[0].strCategory)
        }

        /*
        Button(
            onClick = onToLoginScreenButtonClicked,
            modifier = modifier.widthIn(min = 250.dp)
        ) {
            Text("To Login Screen")
        }*/
    }
}

@Preview
@Composable
fun recipeDetailScreen(){
    RecipeDetailScreen(rememberNavController())
}