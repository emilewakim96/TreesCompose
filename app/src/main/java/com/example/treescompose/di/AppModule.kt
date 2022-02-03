package com.example.treescompose.di

import com.example.treescompose.data.remote.TreesApi
import com.example.treescompose.data.repository.TreesRepository
import com.example.treescompose.util.Constants.BASE_URL
import com.example.treescompose.util.DefaultDispatchers
import com.example.treescompose.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private fun buildOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
//            .addInterceptor(RequestInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = buildOkHttpClient()

    @Singleton
    @Provides
    fun provideTreesRepository(api: TreesApi) = TreesRepository(api)

    @Singleton
    @Provides
    fun provideTreesApi(okHttpClient: OkHttpClient): TreesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(TreesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDispatcher(): DispatcherProvider = DefaultDispatchers()
}