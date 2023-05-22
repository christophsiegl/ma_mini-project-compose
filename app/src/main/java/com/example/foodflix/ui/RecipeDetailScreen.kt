package com.example.foodflix.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.rememberMotionLayoutState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.foodflix.R
import com.example.foodflix.model.MealDetail
import com.example.foodflix.ui.theme.OffBlackBlueHint
import com.example.foodflix.ui.theme.OffBlackBlueHintDarker
import com.example.foodflix.ui.theme.OffBlackBlueHintLighter
import com.example.foodflix.viewmodel.RecipeDetailViewModel
import com.example.foodflix.viewmodel.RecipeDetailViewModelFactory
import com.example.foodflix.viewmodel.SharedViewModel
import com.example.foodflix.workers.RecipeFetchWorker

@OptIn(ExperimentalMotionApi::class)
@Composable
fun RecipeDetailScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    modifier: Modifier = Modifier,
    recipeDetailViewModel: RecipeDetailViewModel = viewModel(
        factory = RecipeDetailViewModelFactory(
            LocalContext.current
        )
    )
) {
    val recipeDetail by recipeDetailViewModel.mealDetails.observeAsState(emptyList())
    val selectedMealId = navController.currentBackStackEntry?.arguments?.getString("mealId")
    val email by sharedViewModel.userMail.collectAsState()

    LaunchedEffect(Unit) {
        recipeDetailViewModel.createWorkManagerTaskMealDetail(
            RecipeFetchWorker.RequestType.GET_RECIPE_DETAIL,
            selectedMealId!!
        )
    }

    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }
    val motionState = rememberMotionLayoutState()
    val corners = 10f - ((motionState.currentProgress * 10)).coerceAtMost(10f)

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        motionLayoutState = motionState,
        modifier = Modifier
            .fillMaxSize()
            .background(OffBlackBlueHintDarker)
    ) {
        if (recipeDetail.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(recipeDetail[0].strMealThumb),
                contentDescription = stringResource(R.string.meal_picture_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .layoutId("headerImage")
                    .background(OffBlackBlueHintDarker)
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(
                        color = OffBlackBlueHintLighter,
                        shape = RoundedCornerShape(topStart = corners, topEnd = corners)
                    )
                    .layoutId("contentBg")
            )

            Text(
                text = recipeDetail[0].strMeal, fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold, modifier = Modifier
                    .layoutId("title")
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            Divider(
                Modifier
                    .layoutId("titleDivider")
                    .fillMaxWidth()
                    .padding(horizontal = 34.dp)
            )

            Text(
                text = "Category: ${recipeDetail[0].strCategory}", fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .layoutId("subTitle")
                    .fillMaxWidth()
                    .padding(6.dp)
            )

            Divider(
                Modifier
                    .layoutId("subTitleDivider")
                    .fillMaxWidth()
                    .padding(horizontal = 34.dp)
            )

            Text(
                modifier = Modifier
                    .layoutId("date")
                    .fillMaxWidth()
                    .padding(6.dp),
                text = "Popular Area: ${recipeDetail[0].strArea}", fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )

            val properties = motionProperties("actions")

            Row(
                modifier = Modifier
                    .layoutId("actions")
                    .background(color = OffBlackBlueHint),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                /*
                IconButton(onClick = { }) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Share, contentDescription = "", tint = Color.White)
                        Text(text = "SHARE", fontSize = 12.sp)
                    }
                }

                IconButton(onClick = { }) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Outlined.ThumbUp, contentDescription = "", tint = Color.White)
                        Text(text = "LIKE", fontSize = 12.sp)
                    }
                }
                */

                IconButton(onClick = {
                    recipeDetailViewModel.setAsFavouriteRecipe(
                        RecipeFetchWorker.RequestType.SET_FAVOURITE_RECIPE,
                        email = email!!,
                        mealID = selectedMealId!!
                    )
                }) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Outlined.Star, contentDescription = "", tint = Color.White)
                        Text(text = "SAVE", fontSize = 12.sp)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .layoutId("text")
                    .background(OffBlackBlueHintLighter)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Instructions",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 25.sp,
                    )
                )

                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(), // Adjust the modifier to fill the available width
                    text = recipeDetail[0].strInstructions,
                    fontSize = 17.sp,
                )

                Spacer(modifier = Modifier.height(8.dp))
                IngredientsTable(mealDetail = recipeDetail[0])
                Spacer(modifier = Modifier.height(8.dp))
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
            .border(1.dp, Color.White)
            .weight(weight)
            .padding(8.dp)
    )
}

//@Preview
@Composable
fun IngredientsTable(mealDetail: MealDetail) {
    // Each cell of a column must have the same weight.
    val column1Weight = .7f // 30%
    val column2Weight = .3f // 70%
    // The LazyColumn will be our table. Notice the use of the weights below
    Column(
        Modifier
            .fillMaxSize()
            .background(OffBlackBlueHintLighter)
            .layoutId("table")
            .padding(12.dp)
    ) {
        // Here is the header
        Row(Modifier.background(OffBlackBlueHint)) {
            TableCell(text = "Ingredient", weight = column1Weight)
            TableCell(text = "Amount", weight = column2Weight)
        }
        if (mealDetail.strIngredient1.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient1, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure1, weight = column2Weight)
            }
        }
        if (mealDetail.strIngredient2.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient2, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure2, weight = column2Weight)
            }
        }
        if (mealDetail.strIngredient3.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient3, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure3, weight = column2Weight)
            }
        }
        if (mealDetail.strIngredient4.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient4, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure4, weight = column2Weight)
            }
        }
        if (mealDetail.strIngredient5.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient5, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure5, weight = column2Weight)
            }
        }
        if (mealDetail.strIngredient6.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient6, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure6, weight = column2Weight)
            }
        }
        if (mealDetail.strIngredient7.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient7, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure7, weight = column2Weight)
            }
        }
        if (mealDetail.strIngredient8.isNotEmpty()) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = mealDetail.strIngredient8, weight = column1Weight)
                TableCell(text = mealDetail.strMeasure8, weight = column2Weight)
            }
        }
    }
}