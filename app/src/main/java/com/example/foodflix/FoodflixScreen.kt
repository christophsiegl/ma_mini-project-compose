package com.example.foodflix

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodflix.database.RecipeDatabase
import com.example.foodflix.repository.RecipeRepositorySingleton
import com.example.foodflix.ui.HomeScreen
import com.example.foodflix.ui.LoginScreen
import com.example.foodflix.ui.theme.Purple500
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.foodflix.ui.ProfileScreen
import kotlinx.coroutines.CoroutineScope
import com.example.foodflix.ui.DiscoverScreen
import com.example.foodflix.viewmodel.SharedViewModel

enum class FoodflixScreen {
    Login,
    Profile,
    Home,
    Discover,
    ThingsAtHome,
    RecipeDetail
}

@Composable
fun FoodflixAppBar(
    sharedViewModel: SharedViewModel,
    navigateUp: () -> Unit,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier
) {
    val canSearchState by sharedViewModel.canSearch.collectAsState()
    val searchFieldVisible by sharedViewModel.searchFieldVisible.collectAsState()
    val searchQuery = remember { mutableStateOf("") }

    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = stringResource(R.string.menu)
                )
            }
        },
        actions = {
            if (canSearchState) {
                if (searchFieldVisible) {
                    // Display the search field
                    OutlinedTextField(
                        value = searchQuery.value,
                        onValueChange = { searchQuery.value = it },
                        placeholder = { Text(text = "Search") },
                        singleLine = true,
                        modifier = Modifier.widthIn(max = 200.dp)
                    )
                } else {
                    // Display the search icon
                    IconButton(onClick = { sharedViewModel.setSearchFieldVisible(true)}) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = stringResource(R.string.search),
                            tint = Color.White,
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun FoodflixApp(modifier: Modifier = Modifier,
                sharedViewModel: SharedViewModel,){

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val bottomNavItems = listOf(
        BottomNavItem(
            name = stringResource(R.string.login),
            route = FoodflixScreen.Login.name,
            icon = Icons.Rounded.AddCircle,
        ),
        BottomNavItem(
            name = stringResource(R.string.home),
            route = FoodflixScreen.Home.name,
            icon = Icons.Rounded.Home,
        ),
        BottomNavItem(
            name = stringResource(R.string.discover),
            route = FoodflixScreen.Discover.name,
            icon = Icons.Rounded.Search,
        ),
        BottomNavItem(
            name = stringResource(R.string.settings),
            route = FoodflixScreen.Profile.name,
            icon = Icons.Rounded.Settings,
        ),
    )

    val drawerWidth = 200.dp

    val recipeRepository = RecipeRepositorySingleton.getInstance(
        RecipeDatabase.getDatabase(LocalContext.current))

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            FoodflixAppBar(
                sharedViewModel = sharedViewModel,
                navigateUp = { /* TODO: implement back navigation */ },
                coroutineScope,
                scaffoldState
            )
        },
        drawerShape = NavShape(drawerWidth, 0f),
        drawerContent = {
            Column(
                modifier = Modifier
                    .width(drawerWidth)
                    .padding(start = 8.dp, top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(126.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        modifier = Modifier
                            .matchParentSize(),
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "",
                    )

                    Image(
                        modifier = Modifier
                            .scale(1.4f),
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "",
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                bottomNavItems.forEach { item ->
                    //val selected = item.route == navController.previousBackStackEntry?.destination?.route
                    val selected = false

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable {
                                coroutineScope.launch {
                                    scaffoldState.drawerState.close()
                                }
                                navController.navigate(item.route)
                            },
                        backgroundColor = if (selected) Purple500 else Color.LightGray,
                        elevation = 0.dp,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = "${item.name} Icon"
                            )

                            Text(
                                modifier = Modifier
                                    .padding(start = 24.dp),
                                text = item.name,
                            )
                        }
                    }
                }
            }
        },
        drawerGesturesEnabled = true,
    ) { innerPadding ->
        //val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = FoodflixScreen.Home.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = FoodflixScreen.Home.name) {
                LaunchedEffect(Unit) {
                    sharedViewModel.setCanSearch(false) // Set canSearch to false
                }
                HomeScreen(
                    //quantityOptions = quantityOptions, //can be used to pass arguments!!
                    onToSearchScreenClicked = {
                        navController.navigate(FoodflixScreen.Discover.name)
                    },
                    onToLoginScreenClicked = {
                        navController.navigate(FoodflixScreen.Login.name)
                    },
                    onToDiscoverScreenClicked = {
                        navController.navigate(FoodflixScreen.Discover.name)
                    }
                )
            }
            composable(route = FoodflixScreen.Login.name) {
                LaunchedEffect(Unit) {
                    sharedViewModel.setCanSearch(false) // Set canSearch to false
                    sharedViewModel.setSearchFieldVisible(false)
                }
                LoginScreen(
                    navController
                )
            }
            composable(route = FoodflixScreen.Discover.name) {
                LaunchedEffect(Unit) {
                    sharedViewModel.setCanSearch(true) // Set canSearch to true only on Discover screen
                    sharedViewModel.setSearchFieldVisible(false)
                }
                DiscoverScreen()
            }
            composable(

                route = "${FoodflixScreen.Profile.name}/{email}",
                arguments = listOf(navArgument("email") { type = NavType.StringType })
            ){
                LaunchedEffect(Unit) {
                    sharedViewModel.setCanSearch(false) // Set canSearch to false
                    sharedViewModel.setSearchFieldVisible(false)
                }
                ProfileScreen(navController = navController)
            }
        }
    }
}

data class BottomNavItem(val name: String, val route: String, val icon: ImageVector)

class NavShape(
    private val widthOffset: Dp,
    private val scale: Float
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(
            Rect(
                Offset.Zero,
                Offset(
                    size.width * scale + with(density) { widthOffset.toPx() },
                    size.height
                )
            )
        )
    }
}