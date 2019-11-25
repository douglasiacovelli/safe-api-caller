package br.com.myapplication

import retrofit2.http.GET

interface CustomService {

    @GET("https://demo8441544.mockable.io/teste")
    suspend fun getPosts(): ResultWrapper<List<String>>
}