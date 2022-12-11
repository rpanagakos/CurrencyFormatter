package com.example.currencyformater.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyformater.presentation.main_screen.MainScreen
import com.example.currencyformater.presentation.main_screen.MainViewModel
import com.example.currencyformater.theme.LocalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocalTheme {
                Surface(color = LocalTheme.colors.Gunmetal) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
                        composable(
                            route = Screen.MainScreen.route
                        ){
                            MainScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }
}