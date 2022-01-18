package com.example.treescompose.data.repository

import com.example.treescompose.data.remote.responses.Trees
import com.example.treescompose.data.remote.TreesApi
import com.example.treescompose.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TreesRepository @Inject constructor(
    private val api: TreesApi
) {
    suspend fun getTreesList(): Resource<Trees> {
        val response = try {
            api.getTreesList()
        } catch(e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
}