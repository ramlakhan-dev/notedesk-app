package com.rl.notedesk.presentation.navigation

sealed class Screen(val route: String) {
    object SignUpScreen: Screen(route = "sign-up-screen")
    object SignInScreen: Screen(route = "sign-in-screen")
    object HomeScreen: Screen(route = "home-screen")
}