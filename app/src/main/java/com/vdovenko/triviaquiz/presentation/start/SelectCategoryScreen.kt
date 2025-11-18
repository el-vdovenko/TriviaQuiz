package com.vdovenko.triviaquiz.presentation.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vdovenko.triviaquiz.domain.entities.Category
import com.vdovenko.triviaquiz.ui.theme.BlueAccent
import com.vdovenko.triviaquiz.ui.theme.BlueCard
import com.vdovenko.triviaquiz.ui.theme.CategoryAnswer
import com.vdovenko.triviaquiz.ui.theme.TriviaQuizTypography
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectCategoryScreen(
    modifier: Modifier = Modifier,
    onClickCategory: (Int) -> Unit
) {

    val viewModel: SelectCategoryViewModel = koinViewModel()
    val screenState by viewModel.screenState.collectAsState()

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        when (val currentState = screenState) {

            SelectCategoryScreenState.Initial -> {
                Text("Initial")
            }

            is SelectCategoryScreenState.CategoriesLoaded -> {
                SelectCategory(
                    categories = currentState.categories,
                    onClickCategory = { onClickCategory(it) }
                )
            }

            is SelectCategoryScreenState.Error -> {
                Text(text = currentState.message)
            }

            SelectCategoryScreenState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = BlueCard,
                        trackColor = BlueAccent
                    )
                }
            }
        }
    }
}

@Composable
fun SelectCategory(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    onClickCategory: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 40.dp, bottom = 12.dp),
            style = TriviaQuizTypography.titleSmall,
            text = "Select Category"
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                CategoryAnswer(
                    text = "All",
                    onClick = { }
                )
            }
            items(items = categories, key = { it.id }) {
                CategoryAnswer(
                    text = it.name,
                    onClick = { onClickCategory(it.id) }
                )
            }
        }
    }
}