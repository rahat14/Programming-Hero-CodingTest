package com.rahat.pro_hero.codingtest.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rahat.pro_hero.codingtest.models.ScoreModel

@Dao
interface ScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHighScore(verse: ScoreModel)

    @Query("SELECT COALESCE(MAX(score), 0) FROM ScoreModel")
    suspend fun getHighScore(): Int


}