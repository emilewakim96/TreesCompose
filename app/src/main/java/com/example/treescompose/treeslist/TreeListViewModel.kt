package com.example.treescompose.treeslist

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treescompose.data.remote.responses.Tree
import com.example.treescompose.data.repository.TreesRepository
import com.example.treescompose.util.Resource
import com.example.treescompose.util.swapList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TreeListViewModel @Inject constructor(
    private val repository: TreesRepository
) : ViewModel() {

    var treesList = mutableStateListOf<Tree>()
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        loadTreeList()
        startTimerToUpdateList()
    }

    fun loadTreeList() {
        viewModelScope.launch {
            isLoading.value = true
            when(val result = repository.getTreesList()) {
                is Resource.Success -> {
                    val records = result.data?.records
                    records?.let { treesList.addAll(records) }
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

    private fun startTimerToUpdateList() {
        Handler(Looper.getMainLooper()).postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            updateTreeList()
        }, 5000)
    }

    private fun updateTreeList() {
        val newList = treesList.toMutableList()
        newList.forEach {
            it.fields.hauteurenm = 20
        }
        treesList.swapList(newList)
    }
}