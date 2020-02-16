package com.yashin_vadim.catfacts

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatFact(
    val text: String
)