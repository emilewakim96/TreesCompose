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

    init {
        loadTreeList()
    }

    fun loadTreeList() {
        viewModelScope.launch {
            when(val result = repository.getTreesList()) {
                is Resource.Success -> {
                    result.data?.records?.let { treesList.value = it }
                    loadError.value = ""
                }
                else -> {
                    loadError.value = result.message!!
                }
            }
        }
    }
}