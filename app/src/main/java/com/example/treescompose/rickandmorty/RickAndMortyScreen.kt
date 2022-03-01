package com.example.treescompose.rickandmorty

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.treescompose.treeslist.RetrySection
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun GithubRepositoriesScreen(
    viewModel: RickAndMortyViewModel = hiltViewModel()
) {
    val loadError = viewModel.loadError.value  /* no need to use remember for viewModel variables */
    val isLoading = viewModel.isLoading.value

    LazyColumn(modifier = Modifier.padding(bottom = 50.dp)) {
        items(viewModel.charactersList) { character ->
            Text(text = character?.name ?: "")
            Spacer(modifier = Modifier.height(5.dp))
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if(loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.getCharacters()
            }
        }
    }
}
