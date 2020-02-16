package com.yashin_vadim.catfacts.model

import com.yashin_vadim.catfacts.model.api.CatFactService
import io.reactivex.Single
import kotlin.random.Random

class CatFactRepository(val cardFactService: CatFactService) {

    fun getFact(): Single<String> {
        return cardFactService.getFacts()
            .map { response ->
                val randomIn = Random.nextInt(0, response.all.size - 1)
                response.all[randomIn].text
            }
    }

}