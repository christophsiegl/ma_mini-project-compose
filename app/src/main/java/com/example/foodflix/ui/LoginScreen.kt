package com.example.foodflix.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.foodflix.R
import androidx.compose.foundation.Image
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodflix.FoodflixScreen
import com.example.foodflix.utils.Constants.NOT_LOGGED_IN
import com.example.foodflix.viewmodel.LoginScreenViewModel
import com.example.foodflix.viewmodel.LoginScreenViewModelFactory
import com.example.foodflix.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    sharedViewModel: SharedViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    loginScreenViewModel: LoginScreenViewModel = viewModel(factory = LoginScreenViewModelFactory())
){
    val corutineScope = rememberCoroutineScope()
    loginScreenViewModel.setContext(LocalContext.current)

    LaunchedEffect(Unit){
        corutineScope.launch { loginScreenViewModel.isUserLoggedIn() }
    }

    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {
        val Title = if (loginScreenViewModel.userIsAuthenticated) {
            stringResource(R.string.logged_in_title)
        } else {
            if (loginScreenViewModel.appJustLaunched) {
                stringResource(R.string.initial_title)
            } else {
                stringResource(R.string.logged_out_title)
            }
        }
        Title(
            text = Title
        )

        if (loginScreenViewModel.userIsAuthenticated) {
            UserInfoRow(
                label = stringResource(R.string.name_label),
                value = loginScreenViewModel.user.name,
            )
            UserInfoRow(
                label = stringResource(R.string.email_label),
                value = loginScreenViewModel.user.email,
            )
            UserPicture(
                url = loginScreenViewModel.user.picture,
                description = loginScreenViewModel.user.name,
            )
            LogButton(
                text = "Go to settings",
                onClick = { navController.navigate(FoodflixScreen.Profile.name)}
            )
        }

        // Button
        // ------
        val buttonText: String
        val onClickAction: () -> Unit
        if (loginScreenViewModel.userIsAuthenticated) {
            buttonText = stringResource(R.string.log_out_button)
            onClickAction = { loginScreenViewModel.logout() }

            sharedViewModel.setUserImageUrl(loginScreenViewModel.userImageUrl)
            sharedViewModel.setUserMail(loginScreenViewModel.user.email)
        } else {
            buttonText = stringResource(R.string.log_in_button)
            onClickAction = { loginScreenViewModel.login() }
            sharedViewModel.setUserMail(NOT_LOGGED_IN)
        }
        LogButton(
            text = buttonText,
            onClick = onClickAction,
        )
    }
}

@Composable
fun Title(  // 1
    text: String,
)
{
    Text(  // 2
        text = text,
        style = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,  // 3
        )
    )
}

@Composable
fun LogButton(
    text: String,
    onClick: () -> Unit,
) {
    Column(  // 2
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(  // 3
            onClick = { onClick() },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
        ) {
            Text(  // 4
                text = text,
                fontSize = 20.sp,
            )
        }
    }
}

@Composable
fun UserInfoRow(
    label: String,
    value: String,
) {
    Row {  // 1
        Text(
            text = label,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        )
        Spacer( // 2
            modifier = Modifier.width(10.dp),
        )
        Text(
            text = value,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontSize = 20.sp,
            )
        )
    }
}

@Composable
fun UserPicture(  // 1
    url: String,
    description: String,
) {
    Column(  // 2
        modifier = Modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(  // 3
            painter = rememberAsyncImagePainter(url),
            contentDescription = description,
            modifier = Modifier
                .fillMaxSize(0.5f),
        )
    }
}