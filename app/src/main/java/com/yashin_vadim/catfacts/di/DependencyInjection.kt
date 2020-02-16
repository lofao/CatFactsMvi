package com.yashin_vadim.catfacts.di

import com.squareup.moshi.Moshi
import com.yashin_vadim.catfacts.model.api.CatFactService
import com.yashin_vadim.catfacts.model.CatFactRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

interface DependencyInjection {
    val catFactRepository: CatFactRepository
}

class DependencyInjectionIml : DependencyInjection {
    override lateinit var catFactRepository: CatFactRepository
    init {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val moshi = Moshi.Builder().build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://cat-fact.herokuapp.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val cardFactService = retrofit.create(CatFactService::class.java)
        catFactRepository = CatFactRepository(cardFactService)
    }
}