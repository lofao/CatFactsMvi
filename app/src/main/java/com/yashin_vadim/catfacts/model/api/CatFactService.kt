package com.yashin_vadim.catfacts.model.api

import com.yashin_vadim.catfacts.model.Response
import io.reactivex.Single
import retrofit2.http.GET

interface CatFactService {
    @GET("facts")
    fun getFacts(): Single<Response>
}