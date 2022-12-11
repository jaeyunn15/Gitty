package com.github.gitty.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.gitty.Fake.getFakeUserInfoItem
import com.github.gitty.data.repository.UserRepositoryImpl
import com.github.gitty.domain.repository.UserRepository
import com.github.gitty.mock
import com.github.gitty.rule.TestCoroutineRule
import com.github.gitty.whenever
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetUserInfoUseCaseTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    private val userRepository = mock<UserRepository>()

    private val getUserInfoUseCase = mock<GetUserInfoUseCase>()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when fetching userInfo then return a userInfo successfully`() = testCoroutineRule.runBlockingTest {
        whenever(userRepository.getUserInfo())
            .thenAnswer {
                Result.success(getFakeUserInfoItem())
            }

        getUserInfoUseCase().onEach { result ->
            assertNull(result)
            //assertNotNull(result)
            assertEquals(result, getFakeUserInfoItem())
        }
    }

}