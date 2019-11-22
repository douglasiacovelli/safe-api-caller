package br.com.myapplication

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositorySafeTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val service: CustomService = mockk()
    private val apiCaller: SafeApiCaller = mockk()
    private val repository = RepositorySafe(service, testDispatcher, apiCaller)

    @Test
    fun `check if safeApiCall was called`() {
        val result = ResultWrapper.Success(listOf("teste"))
        coEvery { apiCaller.safeApiCall<List<String>>(testDispatcher, any()) } returns result

        runBlockingTest {
            assertEquals(result, repository.getPosts())
        }

        coVerify { service.getPosts() }
        coVerify { apiCaller.safeApiCall(testDispatcher) { service.getPosts() } }
    }
}