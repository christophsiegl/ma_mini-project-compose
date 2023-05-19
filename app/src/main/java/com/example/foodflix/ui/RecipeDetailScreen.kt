package com.example.foodflix.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.foodflix.R
import com.example.foodflix.model.MealDetail
import com.example.foodflix.viewmodel.RecipeDetailViewModel
import com.example.foodflix.viewmodel.RecipeDetailViewModelFactory
import com.example.foodflix.workers.RecipeFetchWorker

@Composable
fun RecipeDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    recipeDetailViewModel: RecipeDetailViewModel = viewModel(factory = RecipeDetailViewModelFactory(
        LocalContext.current)
    )
){
    val recipeDetail by recipeDetailViewModel.mealDetails.observeAsState(emptyList())
    val selectedMealId = navController.currentBackStackEntry?.arguments?.getString("mealId")

    LaunchedEffect(Unit){
        recipeDetailViewModel.createWorkManagerTaskMealDetail(RecipeFetchWorker.RequestType.GET_RECIPE_DETAIL ,selectedMealId!!)
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = 0.dp
    ){
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            if (recipeDetail.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(recipeDetail[0].strMealThumb),
                    contentDescription = stringResource(R.string.meal_picture_description),
                    modifier = Modifier.size(226.dp),
                )
                Text(
                    text = recipeDetail[0].strMeal,
                    style = MaterialTheme.typography.h6,
                    fontSize = 30.sp
                )
                Text(
                    text = "Category: ${recipeDetail[0].strCategory}",
                    style = MaterialTheme.typography.h6,
                    fontSize = 18.sp
                )
                Text(
                    text = "Popular Area: ${recipeDetail[0].strArea}",
                    style = MaterialTheme.typography.h6,
                    fontSize = 18.sp
                )
                Text(
                    text = "Tags: ${recipeDetail[0].strTags}",
                    style = MaterialTheme.typography.h6,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))
                ingreadientsTable(mealDetail = recipeDetail[0])
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Instruction:", style = MaterialTheme.typography.h6, fontSize = 20.sp)
                Text(
                    text = recipeDetail[0].strInstructions,
                    style = MaterialTheme.typography.h6,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { recipeDetailViewModel.setAsFavouriteRecipe(RecipeFetchWorker.RequestType.SET_FAVOURITE_RECIPE, selectedMealId!!) },
                    modifier = modifier.widthIn(min = 250.dp)
                ) {
                    Text("Save Recipe")
                }
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}

//@Preview
@Composable
fun ingreadientsTable(mealDetail: MealDetail){
    // Each cell of a column must have the same weight.
    val column1Weight = .7f // 30%
    val column2Weight = .3f // 70%
    // The LazyColumn will be our table. Notice the use of the weights below
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        // Here is the header
        Row(Modifier.background(Color.Gray)) {
            TableCell(text = "Ingredient", weight = column1Weight)
            TableCell(text = "Amount", weight = column2Weight)
        }
        if(mealDetail.strIngredient1.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient1, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure1, weight = column2Weight)
            }
        }
        if(mealDetail.strIngredient2.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient2, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure2, weight = column2Weight)
            }
        }
        if(mealDetail.strIngredient3.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient3, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure3, weight = column2Weight)
            }
        }
        if(mealDetail.strIngredient4.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient4, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure4, weight = column2Weight)
            }
        }
        if(mealDetail.strIngredient5.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient5, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure5, weight = column2Weight)
            }
        }
        if(mealDetail.strIngredient6.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient6, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure6, weight = column2Weight)
            }
        }
        if(mealDetail.strIngredient7.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient7, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure7, weight = column2Weight)
            }
        }
        if(mealDetail.strIngredient8.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient8, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure8, weight = column2Weight)
            }
        }
    }
}