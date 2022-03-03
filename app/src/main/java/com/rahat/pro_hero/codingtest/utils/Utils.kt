package com.rahat.pro_hero.codingtest.utils

import com.rahat.pro_hero.codingtest.models.AnswerModel
import com.rahat.pro_hero.codingtest.models.Answers

class Utils {
    companion object {
        fun convertObjectToMap(answers: Answers): Map<String, Any> {
            return answers.serializeToMap()
        }

        fun convertHashMapToCustomModel(map: Map<String, Any>, ans: String): List<AnswerModel> {
            val list: MutableList<AnswerModel> = mutableListOf()
            for ((key, value) in map.entries) {
                var isRight = false
                if (key == ans) isRight = true
                list.add(AnswerModel(value.toString(), 0, isRight))
            }
            return list
        }

    }
}