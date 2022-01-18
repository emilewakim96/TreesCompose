package com.example.treescompose.treeslist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treescompose.data.remote.responses.Tree
import com.example.treescompose.data.repository.TreesRepository
import com.example.treescompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TreeListViewModel @Inject constructor(
    private val repository: TreesRepository
) : ViewModel() {

    var treesList = mutableStateOf<List<Tree>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        loadTreeList()
    }

    fun loadTreeList() {
        viewModelScope.launch {
            isLoading.value = true
            when(val result = repository.getTreesList()) {
                is Resource.Success -> {
                    val records = result.data?.records
                    records?.forEach { tree ->
                        tree.fields.adresse = tree.fields.adresse.replace('/', '|', ignoreCase = true)
                    }
                    records?.let { treesList.value = it }
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