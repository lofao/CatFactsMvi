package com.yashin_vadim.catfacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import retrofit2.Retrofit
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val moshi = Moshi.Builder().build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://cat-fact.herokuapp.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val cardFactService = retrofit.create(CatFactService::class.java)

        getFactButton.setOnClickListener {
            val call = cardFactService.getFacts()
            loadingIndicator.isVisible = true
            getFactButton.isEnabled = false
            errorView.isVisible = false
            call.enqueue(object : Callback<Response> {
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    loadingIndicator.isVisible = false
                    getFactButton.isEnabled = true
                    errorView.isVisible = true
                }

                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    loadingIndicator.isVisible = false
                    getFactButton.isEnabled = true
                    errorView.isVisible = false
                    val randomIn = Random.nextInt(0, response.body()!!.all.size - 1)
                    catFactView.text = response.body()!!.all[randomIn].text
                }
            })
        }

    }
}
