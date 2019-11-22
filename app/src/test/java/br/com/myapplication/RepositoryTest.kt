package br.com.myapplication

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryTest {


    private val testDispatcher = TestCoroutineDispatcher()
    private val service: CustomService = mockk()
    private val repository = Repository(service, testDispatcher)

    @Test
    fun `check if safeApiCall was called`() {
        coEvery { service.getPosts() } returns listOf("teste")

        runBlockingTest {
            val result = repository.getPosts()

            assertEquals(ResultWrapper.Success(listOf("teste")), result)
        }

        coVerify { service.getPosts() }
    }

}