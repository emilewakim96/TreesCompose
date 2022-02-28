package com.example.treescompose.githubrepositories

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treescompose.GetCharactersQuery
import com.example.treescompose.data.repository.GithubRepository
import com.example.treescompose.util.DispatcherProvider
import com.example.treescompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubRepositoryViewModel @Inject constructor(
    private val repository: GithubRepository,
    private val dispatcher: DispatcherProvider
): ViewModel() {

    val charactersList = mutableStateListOf<GetCharactersQuery.Result?>()
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        getCharacters()
    }

    fun getCharacters() {
        isLoading.value = true
        viewModelScope.launch(dispatcher.main) {
            when(val result = repository.retrieveData(page = 0)) {
                is Resource.Success -> {
                    val characterResults = result.data?.characters?.results
                    characterResults?.let { charactersList.addAll(it) }
                    loadError.value = ""
                    isLoading.value = false
                }
                else -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
}