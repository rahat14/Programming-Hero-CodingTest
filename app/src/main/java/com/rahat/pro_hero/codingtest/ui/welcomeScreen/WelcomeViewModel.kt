package com.rahat.pro_hero.codingtest.ui.welcomeScreen

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahat.pro_hero.codingtest.local.ScoreDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("SetTextI18n")
@HiltViewModel
class WelcomeViewModel @Inject constructor(
    localDb: ScoreDao
) : ViewModel() {
    private val localDao = localDb
    var userHighScore: MutableLiveData<Int> = MutableLiveData(0)

    init {
        getHighScore()
    }

    private fun getHighScore() {
        viewModelScope.launch {
            userHighScore.value = localDao.getHighScore()
        }
    }

}