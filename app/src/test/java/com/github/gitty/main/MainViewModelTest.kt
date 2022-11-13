package com.github.gitty.main

import com.github.gitty.ViewModelRule
import com.github.gitty.data.repository.AccessRepositoryImpl
import com.github.gitty.data.service.AccessService
import com.github.gitty.domain.repository.AccessRepository
import com.github.gitty.domain.usecase.RequestAccessTokenUseCase
import com.github.gitty.domain.user.UserController
import com.github.gitty.domain.user.UserControllerImpl
import com.github.gitty.ui.activity.MainViewModel
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get: Rule
    val dispatcherRule = ViewModelRule()

    //private lateinit var viewModel: MainViewModel
    private lateinit var requestAccessTokenUseCase: RequestAccessTokenUseCase
    private lateinit var accessRepository: AccessRepository
    private lateinit var accessService: AccessService
    @Mock
    private val userController: UserController = UserControllerImpl()

    @Before
    fun setUp() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
//        accessService = mock(AccessService::class.java)
//        accessRepository = mock(AccessRepositoryImpl)
//        requestAccessTokenUseCase = RequestAccessTokenUseCase(accessRepository)
//        viewModel = MainViewModel(
//            ioDispatcher = dispatcher,
//            requestAccessTokenUseCase = Mockito.mock(RequestAccessTokenUseCase::class.java)
//        )
        //userController = mock(UserControllerImpl::class.java)
    }

    @Test
    fun userLogoutTest() {
        assertTrue(!userController.userLoginState.value)

        userController.updateUserLoginState(true)
        assertTrue(userController.userLoginState.value)

        userController.updateUserLoginState(false)
        assertTrue(!userController.userLoginState.value)
    }

    @Test
    fun getAccessTokenTest() = runTest {
        requestAccessTokenUseCase = mock(RequestAccessTokenUseCase::class.java)
        val result = requestAccessTokenUseCase.invoke("")
//        userController.updateUserLoginState(result.isSuccess)
//        assertTrue(userController.userLoginState.value)
    }
}