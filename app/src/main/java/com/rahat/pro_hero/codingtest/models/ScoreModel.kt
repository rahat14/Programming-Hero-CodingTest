package com.rahat.pro_hero.codingtest.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScoreModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var score: Int = 0

)
