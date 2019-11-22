package br.com.myapplication

import retrofit2.http.GET

interface CustomService {

    @GET("https://bla.com")
    fun getPosts(): List<String>
}