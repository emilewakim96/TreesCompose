package com.example.treescompose.treeslist

import com.example.treescompose.data.remote.responses.Fields
import com.example.treescompose.data.remote.responses.Tree
import com.example.treescompose.data.remote.responses.Trees
import com.example.treescompose.data.repository.TreesRepository
import com.example.treescompose.util.Resource
import com.example.treescompose.utils.TestDispatchers
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TreeListViewModelTest{

    private lateinit var testDispatcher: TestDispatchers

    @Before
    fun setUp() {
        testDispatcher = TestDispatchers()
    }

    @Test
    fun testAdd() {
        assertEquals(42, Integer.sum(19, 23))
    }

    @Test
    fun test_determineTreesListEmptyCount() = runTest {
        val expected = 0
        val trees = Resource.Success(Trees(records = emptyList()))
        val treesRepository: TreesRepository = mock {
            onBlocking {
                getTreesList()
            } doReturn trees
        }
        val viewModel = TreeListViewModel(treesRepository, testDispatcher)
        val actual = viewModel.treesList.size
        assertEquals(expected, actual)
    }

    @Test
    fun test_determineTreesListSingleCount() = runTest {
        val expected = 1
        val trees = Resource.Success(Trees(records = arrayListOf(
            Tree(datasetid = "1", fields = Fields(adresse = "Fanar"), recordid = "1")
        )))
        val treesRepository: TreesRepository = mock {
            onBlocking {
                getTreesList()
            } doReturn trees
        }
        val viewModel = TreeListViewModel(treesRepository, testDispatcher)
        val actual = viewModel.treesList.size
        assertEquals(expected, actual)
    }

    @Test
    fun test_determineTreesListMultipleCount() {
        val expected = 3
        val treesList = listOf (
            Tree(datasetid = "1", fields = Fields(adresse = "Fanar"), recordid = "1"),
            Tree(datasetid = "2", fields = Fields(adresse = "Belgique"), recordid = "2"),
            Tree(datasetid = "3", fields = Fields(adresse = "Beirut"), recordid = "3")
        )
        val trees = Resource.Success(Trees(records = treesList))

        val treesRepository: TreesRepository = mock {
            onBlocking {
                getTreesList()
            } doReturn trees
        }
        val viewModel = TreeListViewModel(treesRepository, testDispatcher)
        val actual = viewModel.treesList.size
        assertEquals(expected, actual)
    }

    @Test
    fun testAllEmissions() = runTest {
        val values = mutableListOf<Int>()
        val stateFlow = MutableStateFlow(0)
        val job = launch(UnconfinedTestDispatcher(testScheduler)) { // <------
            stateFlow.collect {
                values.add(it)
            }
        }
        stateFlow.value = 1
        stateFlow.value = 2
        stateFlow.value = 3
        job.cancel()
        // each assignment will immediately resume the collecting child coroutine,
        // so no values will be skipped.
        assertEquals(listOf(0, 1, 2, 3), values)
    }
}