package com.example.treescompose.utils

import com.example.treescompose.util.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher


@ExperimentalCoroutinesApi
class TestDispatchers: DispatcherProvider {
    val testCoroutineDispatcher = TestCoroutineDispatcher()
//val testCoroutineDispatcher = StandardTestDispatcher()
    override val main: CoroutineDispatcher
        get() = testCoroutineDispatcher
    override val io: CoroutineDispatcher
        get() = testCoroutineDispatcher
    override val default: CoroutineDispatcher
        get() = testCoroutineDispatcher
}