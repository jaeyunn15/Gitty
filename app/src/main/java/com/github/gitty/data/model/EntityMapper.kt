package com.github.gitty.data.model

interface EntityMapper<in M, out E> {
    fun mapFromRemote(model: M): E
}