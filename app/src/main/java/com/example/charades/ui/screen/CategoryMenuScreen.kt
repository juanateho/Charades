package com.example.charades.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.charades.R

@Composable
fun CategoryMenuScreen(
    buttonColor: Color, // Added buttonColor parameter
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf(
        stringResource(R.string.category_animals),
        stringResource(R.string.category_movies),
        stringResource(R.string.category_professions)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.select_category),
            fontSize = 28.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(32.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                Button(
                    onClick = { onCategorySelected(category) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor) // Use passed buttonColor
                ) {
                    Text(text = category, color = Color.White, fontSize = 20.sp)
                }
            }
        }
    }
}
