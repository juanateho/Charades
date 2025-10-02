package com.example.charades.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
	language: String,
	onCategorySelected: (String) -> Unit
) {
	val categories = if (language == "en") {
		listOf(
			stringResource(R.string.category_animals),
			stringResource(R.string.category_movies),
			stringResource(R.string.category_professions)
		)
	} else {
		listOf(
			stringResource(R.string.category_animals),
			stringResource(R.string.category_movies),
			stringResource(R.string.category_professions)
		)
	}

	Surface(
		modifier = Modifier.fillMaxSize(),
		color = MaterialTheme.colorScheme.background
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.background(MaterialTheme.colorScheme.background)
				.padding(32.dp),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				text = stringResource(R.string.select_category),
				fontSize = 28.sp,
				color = MaterialTheme.colorScheme.primary
			)
			Spacer(modifier = Modifier.height(32.dp))
			categories.forEach { category ->
				Button(
					onClick = { onCategorySelected(category) },
					modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
					colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
				) {
					Text(text = category, color = Color.White, fontSize = 20.sp)
				}
			}
		}
	}
}
