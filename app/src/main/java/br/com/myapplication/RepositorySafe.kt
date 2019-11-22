package br.com.myapplication

import kotlinx.coroutines.CoroutineDispatcher

class RepositorySafe(
    private val service: CustomService,
    private val dispatcher: CoroutineDispatcher,
    private val apiCaller: SafeApiCaller
) {

    suspend fun getPosts(): ResultWrapper<List<String>> {
        return apiCaller.safeApiCall(dispatcher) { service.getPosts() }
    }
}