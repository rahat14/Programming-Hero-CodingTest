package com.rahat.pro_hero.codingtest.models

data class Answers(
    val A: String? = null,
    val B: String? = null,
    val C: String? = null,
    val D: String? = null
)
data class AnswerModel(
    val answer: String? = null,
    var checker: Int?  = 0,
    val isRight: Boolean = false

)