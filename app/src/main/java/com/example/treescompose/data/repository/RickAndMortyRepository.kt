package com.example.treescompose.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import com.example.treescompose.GetCharactersQuery
import com.example.treescompose.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RickAndMortyRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {

    suspend fun retrieveCharacters(page: Int): Resource<GetCharactersQuery.Data?> {
        val result = try {
            apolloClient.query(GetCharactersQuery(page = page.toInput())).await()
        } catch (ex: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return when {
            result.hasErrors() || !result.errors.isNullOrEmpty() -> Resource.Error(
                result.errors?.first()?.message ?: "An unkown message occured"
            )
            else -> Resource.Success(result.data)
        }
    }
}