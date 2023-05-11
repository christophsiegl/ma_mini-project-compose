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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodflix.ui.HomeScreen
import com.example.foodflix.ui.LoginScreen
import com.example.foodflix.ui.SearchScreen
import com.example.foodflix.ui.theme.Purple500
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.foodflix.ui.ProfileScreen
import kotlinx.coroutines.CoroutineScope

enum class FoodflixScreen() {
    Login,
    Profile,
    Home,
    Search,
    ThingsAtHome,
    Recipe
}

@Composable
fun FoodflixAppBar(
    canSearch: Boolean,
    navigateUp: () -> Unit,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier
) {
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
            if(canSearch) {
                IconButton(onClick = { /*TODO: Implement search functionality if necessary */}) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource(R.string.search),
                        tint = Color.White,
                    )
                }
            }
        }
    )
}

@Composable
fun FoodflixApp(modifier: Modifier = Modifier){
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
            route = FoodflixScreen.Search.name,
            icon = Icons.Rounded.Search,
        ),
        BottomNavItem(
            name = stringResource(R.string.settings),
            route = FoodflixScreen.Profile.name,
            icon = Icons.Rounded.Settings,
        ),
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            FoodflixAppBar(
                canSearch = false,
                navigateUp = { /* TODO: implement back navigation */ },
                coroutineScope,
                scaffoldState
            )
        },
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                    val selected = item.route == navController.currentBackStackEntry?.destination?.route

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
                HomeScreen(
                    //quantityOptions = quantityOptions, //can be used to pass arguments!!
                    onToSearchScreenClicked = {
                        navController.navigate(FoodflixScreen.Search.name)
                    },
                    onToLoginScreenClicked = {
                        navController.navigate(FoodflixScreen.Login.name)
                    }
                )
            }
            composable(route = FoodflixScreen.Login.name) {
                LoginScreen(
                    onToHomeScreenButtonClicked = {
                        navController.navigate(FoodflixScreen.Home.name)
                    }
                )
            }
            composable(route = FoodflixScreen.Search.name) {
                SearchScreen(
                    onToLoginScreenButtonClicked = {
                        navController.navigate(FoodflixScreen.Login.name)
                    }
                )
            }
            composable(route = FoodflixScreen.Profile.name) {
                ProfileScreen()
            }
        }
    }
}

data class BottomNavItem(val name: String, val route: String, val icon: ImageVector)