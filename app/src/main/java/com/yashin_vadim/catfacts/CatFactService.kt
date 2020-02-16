package com.yashin_vadim.catfacts

import retrofit2.Call
import retrofit2.http.GET

interface CatFactService {
    @GET("facts")
    fun getFacts(): Call<Response>
}