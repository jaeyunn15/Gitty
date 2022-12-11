package com.github.gitty.di

import kotlinx.coroutines.CoroutineDispatcher

public interface DispatcherProvider {
    @DefaultDispatcher
    val default: CoroutineDispatcher
    @IoDispatcher
    val io: CoroutineDispatcher
    @MainDispatcher
    val main: CoroutineDispatcher
    @MainImmediateDispatcher
    val mainImmediate: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}