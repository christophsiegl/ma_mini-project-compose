package com.example.foodflix.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.foodflix.model.Meal
import com.example.foodflix.repository.RecipeRepository
import com.example.foodflix.workers.RecipeFetchWorker
import kotlinx.coroutines.launch


@Composable
fun BrowseScreen(modifier: Modifier = Modifier, recipeRepository: RecipeRepository) {
    val recipes by recipeRepository.recipes.observeAsState(emptyList())

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val workManager = WorkManager.getInstance(context)

    LaunchedEffect(Unit) {
        // Enqueue the worker when entering the BrowseScreen
        val request = OneTimeWorkRequestBuilder<RecipeFetchWorker>().build()
        workManager.enqueue(request)
    }

    Column {
        Text(text = "Browse Screen")
        Spacer(modifier = Modifier.height(8.dp))
        BrowseContent(recipes = recipes)
    }


}
@Composable
fun BrowseContent(recipes: List<Meal>) {
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
            Text(text = meal.strMeal, style = MaterialTheme.typography.h6)
            Text(text = "VIEW DETAIL", style = MaterialTheme.typography.caption)
        }
    }
}