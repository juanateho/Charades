package com.example.charades.navigation

sealed class Screen(val route: String) {
    object CategoryScreen : Screen("category_screen")
    object GameScreen : Screen("game_screen/{category}") {
        fun createRoute(category: String) = "game_screen/$category"
    }
    object ResultScreen : Screen("result_screen/{score}") {
        fun createRoute(score: Int) = "result_screen/$score"
    }
}