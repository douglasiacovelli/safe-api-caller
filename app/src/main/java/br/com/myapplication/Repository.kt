package br.com.myapplication

import kotlinx.coroutines.CoroutineDispatcher

class Repository(
    private val service: CustomService,
    private val dispatcher: CoroutineDispatcher
) {


}