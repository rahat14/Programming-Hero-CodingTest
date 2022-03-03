package com.rahat.pro_hero.codingtest.ui.quiz

import com.haroldadmin.cnradapter.NetworkResponse
import com.rahat.pro_hero.codingtest.models.ErrorResponse
import com.rahat.pro_hero.codingtest.models.QusResponse
import com.rahat.pro_hero.codingtest.networking.ApiInterface

class QuizRepository(private val api: ApiInterface) {

    suspend fun getQuiz(
    ): Pair<QusResponse?, ErrorResponse?> {

        return when (val response = api.getQuizList()) {
            is NetworkResponse.Success -> {
                // Handle successful response
                Pair(response.body, null)
            }

            is NetworkResponse.ServerError -> {
                // Handle server error
                Pair(null, ErrorResponse(400, "Server Error"))
            }
            is NetworkResponse.NetworkError -> {
                // Handle network error
                Pair(null, ErrorResponse(400, "Network Error"))
            }
            is NetworkResponse.UnknownError -> {
                // Handle other errors
                Pair(null, ErrorResponse(400, "Unknown Error"))
            }
        }

    }


}