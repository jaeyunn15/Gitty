package com.github.gitty

import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {

    val main
        get() = Dispatchers.Main.immediate
    val default
        get() = Dispatchers.Default
    val io
        get() = Dispatchers.IO
    val unconfined
        get() = Dispatchers.Unconfined
}