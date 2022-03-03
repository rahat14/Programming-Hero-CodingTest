package com.rahat.pro_hero.codingtest.ui.quiz

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.widget.TextView
import androidx.lifecycle.*
import com.rahat.pro_hero.codingtest.local.ScoreDao
import com.rahat.pro_hero.codingtest.models.*
import com.rahat.pro_hero.codingtest.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@SuppressLint("SetTextI18n")
@HiltViewModel
class QuizViewModel @Inject constructor(
    repository: QuizRepository,
    localDb: ScoreDao
) : ViewModel() {

    private val repos = repository
    private val localDao = localDb
    var totalUserScore = 0
    private var currentQusIndex = 0
    val currentTime = MutableLiveData<Long>()
    private lateinit var timer: CountDownTimer
    var qusResponse: MutableLiveData<Pair<QusResponse?, ErrorResponse?>> = MutableLiveData()
    var nextQuestionTimer = TimeUnit.SECONDS.toMillis(2)
    var OnGoingQuestionTimer = TimeUnit.SECONDS.toMillis(10)
    var triggerNextQuestion = MutableLiveData<Boolean>(false)


    /*
    this will invoke when viewmodel create itself
     */
    init {

        loadQuizList()
    }

    fun getQuizList(): LiveData<Pair<QusResponse?, ErrorResponse?>> {
        // returning the live data in main thread as retrofit handle the thread auto.
        return liveData(Dispatchers.Main) {
            val pair = repos.getQuiz()
            emit(pair)
        }

    }

    private fun loadQuizList() {
        viewModelScope.launch {
            qusResponse.value = repos.getQuiz()
        }

    }

    fun getCurrentQuestion(): Question? {

        return qusResponse.value?.first?.questions?.get(currentQusIndex)
    }


    fun checkAndIncrementQusIndex(): Boolean {
        val size = qusResponse.value?.first?.questions?.size
        return if (size != null) {
            if ((size - 1) > currentQusIndex) {
                currentQusIndex += 1
                true
            } else {
                false
            }

        } else false
    }

    fun incrementPoint() {
        val currentQuestionScore = qusResponse.value?.first?.questions?.get(currentQusIndex)?.score
        if (currentQuestionScore != null) {
            totalUserScore += currentQuestionScore
        }
    }

    fun addScoreToDatabase() {
        val localScoreModel = ScoreModel()
        localScoreModel.score = totalUserScore
        viewModelScope.launch {
            localDao.insertHighScore(localScoreModel)
        }
    }

    fun updateTheQuestionCounter(pointTv: TextView) {
        pointTv.text =
            "Question: ${currentQusIndex + 1}/${qusResponse.value?.first?.questions?.size}"
    }

    fun updateThePointTv(pointTv: TextView) {
        val qusItm = qusResponse.value?.first?.questions?.get(currentQusIndex)
        pointTv.text = "${qusItm?.score} Point"
    }

    fun convertListToOwnAnswerList(answers: Answers?, correctAnswer: String?): List<AnswerModel>? {
        // convert it hashmap to get the A , B , C keys
        val map = answers?.let { Utils.convertObjectToMap(it) }

        // then convert it our map
        val list = map?.let { Utils.convertHashMapToCustomModel(it, correctAnswer.toString()) }
        return list
    }

    fun calculateTheAnswer(item: AnswerModel) {

        if (item.isRight) {
            totalUserScore += getCurrentQuestion()?.score?.toInt() ?: 0 // assign default value
        }

    }

    fun updateTheUserPointTv(scroeTv: TextView) {
        scroeTv.text = "Score : $totalUserScore"
    }


    fun createAndStartTimerForNextQuestion() {
        if (timer != null) {
            timer.cancel()
        }
        timer = object : CountDownTimer(nextQuestionTimer, 1000L) {
            override fun onTick(p0: Long) {
                currentTime.value = p0 / 1000L
            }

            override fun onFinish() {
                triggerNextQuestion.value = true
            }

        }
        timer.start()
    }

    fun createAndStartTimerForOngoingQuestion() {

        timer = object : CountDownTimer(OnGoingQuestionTimer, 1000L) {
            override fun onTick(p0: Long) {
                currentTime.value = p0 / 1000L
            }

            override fun onFinish() {
                triggerNextQuestion.value = true
            }

        }

        timer.start()


    }


    override fun onCleared() {
        timer.cancel()
        super.onCleared()
    }

    fun continueQuestion(): Boolean {
        val size = qusResponse.value?.first?.questions?.size
        return if (size != null) {
            (size - 1) > currentQusIndex
        } else false
    }

    fun cancelAllTimer() {
        try {
            timer.cancel()
        } catch (Ex: Exception) {

        }
    }
}