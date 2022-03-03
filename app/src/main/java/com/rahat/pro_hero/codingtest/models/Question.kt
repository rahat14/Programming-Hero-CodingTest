package com.rahat.pro_hero.codingtest.models

data class Question(
    val answers: Answers? = null,
    val correctAnswer: String? = null,
    val question: String? = null,
    val questionImageUrl: String? = null,
    val score: Int? = null
)