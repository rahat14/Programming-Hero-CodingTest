package com.rahat.pro_hero.codingtest.networking

import com.haroldadmin.cnradapter.NetworkResponse
import com.rahat.pro_hero.codingtest.models.ErrorResponse
import com.rahat.pro_hero.codingtest.models.QusResponse
import retrofit2.http.GET

interface ApiInterface {

    @GET("quiz.json")
    suspend fun getQuizList(
    ): NetworkResponse<QusResponse, ErrorResponse> // as i do not know any Error response so, i made this generic to handle error

}