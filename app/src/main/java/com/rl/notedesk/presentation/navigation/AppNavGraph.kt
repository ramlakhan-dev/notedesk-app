package com.rl.notedesk.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rl.notedesk.presentation.screen.home.HomeScreen
import com.rl.notedesk.presentation.screen.signin.SignInScreen
import com.rl.notedesk.presentation.screen.signup.SignUpScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isAuthenticated: Boolean
) {

    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) Screen.HomeScreen.route else Screen.SignInScreen.route
    ) {
        composable(
            route = Screen.SignUpScreen.route
        ) {
            SignUpScreen(
                modifier = modifier,
                navController = navController
            )
        }

        composable(
            route = Screen.SignInScreen.route
        ) {
            SignInScreen(
                modifier = modifier,
                navController = navController
            )
        }

        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(
                modifier = modifier,
                navController = navController
            )
        }
    }

}