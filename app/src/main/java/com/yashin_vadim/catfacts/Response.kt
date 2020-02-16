package com.yashin_vadim.catfacts

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    val all: List<CatFact>
)