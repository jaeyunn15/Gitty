package com.github.gitty.domain.usecase

import com.github.gitty.di.IoDispatcher
import com.github.gitty.domain.entity.RepositoryItem
import com.github.gitty.domain.repository.GitRepositoryRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetRepositoriesUseCase @Inject constructor(
    private val gitRepositoryRepo: GitRepositoryRepo,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(query: String): Flow<List<RepositoryItem>> {
        return flow {
            emit(gitRepositoryRepo.getRepositories(query))
        }.flowOn(ioDispatcher)
    }
}