package com.example.treescompose.treeslist

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treescompose.data.remote.responses.Tree
import com.example.treescompose.data.repository.TreesRepository
import com.example.treescompose.util.DefaultDispatchers
import com.example.treescompose.util.DispatcherProvider
import com.example.treescompose.util.Resource
import com.example.treescompose.util.swapList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TreeListViewModel @Inject constructor(
    private val repository: TreesRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    var treesList = mutableStateListOf<Tree>()
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var scrollToIndex = mutableStateOf<Int?>(null)

    init {
        loadTreeList()
        startTimerToUpdateList()
        scrollToPos(index = 5)
    }

    fun loadTreeList() {
        viewModelScope.launch(dispatcher.main) {
            isLoading.value = true
            when(val result = repository.getTreesList()) {
                is Resource.Success -> {
                    val records = result.data?.records
                    records?.forEachIndexed { index, tree ->
                        if (index == 0) {
                            tree.fields.image = "https://source.unsplash.com/user/c_v_r/1900x800"
                            return@forEachIndexed
                        }
                    }
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
        viewModelScope.launch(dispatcher.main) {
            delay(5000)
            updateTreeList()
        }
    }

    private fun scrollToPos(index : Int?) {
        viewModelScope.launch(dispatcher.main) {
            delay(8000)
            scrollToIndex.value = index
        }
    }

    private fun updateTreeList() {
        val newList = treesList.toMutableList()
        if (newList.isNotEmpty()) {
            newList.forEach {
                it.fields.hauteurenm = 20
            }
            newList.add(newList.removeAt(0)) /* move first element to last element */
            treesList.swapList(newList)
        }
    }
}