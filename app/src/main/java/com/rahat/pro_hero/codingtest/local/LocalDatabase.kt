package com.rahat.pro_hero.codingtest.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rahat.pro_hero.codingtest.models.ScoreModel

@Database(entities = [ScoreModel::class], version = 2)
abstract class LocalDatabase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context): LocalDatabase {
            return Room.databaseBuilder(context, LocalDatabase::class.java, "quiz_hero_database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun getScoreDao(): ScoreDao

}