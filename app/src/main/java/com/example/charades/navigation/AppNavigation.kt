package com.example.charades.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.charades.ui.screens.CategoryScreen
import com.example.charades.ui.screens.GameScreen
import com.example.charades.ui.screens.ResultScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.CategoryScreen.route) {
        composable(Screen.CategoryScreen.route) {
            CategoryScreen(navController = navController)
        }
        composable(Screen.GameScreen.route) {
            val category = it.arguments?.getString("category") ?: ""
            GameScreen(navController = navController, category = category)
        }
        composable(Screen.ResultScreen.route) {
            val score = it.arguments?.getString("score")?.toInt() ?: 0
            ResultScreen(navController = navController, score = score)
        }
    }
}