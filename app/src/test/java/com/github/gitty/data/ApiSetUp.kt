package com.github.gitty.data

import com.github.gitty.data.repository.AccessRepositoryImpl
import com.github.gitty.data.service.AccessService
import com.github.gitty.domain.repository.AccessRepository
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.mockito.Mockito.mock


private lateinit var repository: AccessRepository
private lateinit var testApis: AccessService
private lateinit var mockWebServer: MockWebServer

@Before
fun setUp() {
    mockWebServer = MockWebServer()
    mockWebServer.start()
    //testApis = RetrofitHelper.testApiInstance(mockWebServer.url("/").toString())
    repository = AccessRepositoryImpl(testApis)
}



@After
fun tearDown() {
    mockWebServer.shutdown()
}
