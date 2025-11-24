package com.rl.notedesk.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rl.notedesk.R
import com.rl.notedesk.presentation.navigation.AppNavGraph
import com.rl.notedesk.presentation.navigation.Screen
import com.rl.notedesk.presentation.state.AuthState
import com.rl.notedesk.presentation.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDeskApp() {

    val authViewModel: AuthViewModel = hiltViewModel()
    val userState by authViewModel.userState.collectAsState()

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val title = when(currentRoute) {
        Screen.HomeScreen.route -> stringResource(id = R.string.app_name)
        else -> "Hello"
    }

    var showMenu by remember { mutableStateOf(false) }

    val signOutState by authViewModel.signOutState.collectAsState()
    LaunchedEffect(signOutState) {
        if (signOutState is AuthState.Unauthenticated) {
            navController.navigate(route = Screen.SignInScreen.route) {
                popUpTo(route = Screen.HomeScreen.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (currentRoute != Screen.SignUpScreen.route && currentRoute != Screen.SignInScreen.route) {
                TopAppBar(
                    title = {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        if (currentRoute != Screen.HomeScreen.route) {
                            IconButton(
                                onClick = {
                                    navController.popBackStack()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(id = R.string.back)
                                )
                            }
                        }
                    },
                    actions = {
                        if (currentRoute == Screen.HomeScreen.route) {
                            IconButton(
                                onClick = {
                                    showMenu = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = ""
                                )
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = {
                                    showMenu = false
                                }
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.sign_out)
                                        )
                                    },
                                    onClick = {
                                        showMenu = false
                                        authViewModel.signOut()
                                    }
                                )
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        AppNavGraph(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            isAuthenticated = userState is AuthState.Authenticated
        )
    }
}