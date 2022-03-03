package com.rahat.pro_hero.codingtest.ui.quiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rahat.pro_hero.codingtest.R
import com.rahat.pro_hero.codingtest.adapters.AnswerAdapter
import com.rahat.pro_hero.codingtest.databinding.FragmentQuizBinding
import com.rahat.pro_hero.codingtest.models.AnswerModel
import com.rahat.pro_hero.codingtest.utils.Const
import com.rahat.pro_hero.codingtest.utils.fadeTo
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuizFragment : Fragment(R.layout.fragment_quiz), AnswerAdapter.Interaction {
    private val viewModel: QuizViewModel by viewModels()
    private lateinit var binding: FragmentQuizBinding
    private lateinit var mAdapter: AnswerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuizBinding.bind(view)
        mAdapter = AnswerAdapter(this, emptyList())

        setView()
        ovservers()
        callForData()
    }

    private fun ovservers() {

        viewModel.currentTime.observe(viewLifecycleOwner) {
            binding.circularProgressBar.progress =
                binding.circularProgressBar.progressMax - it.toFloat()
            binding.timerTv.text = "$it Sec"
        }

        viewModel.triggerNextQuestion.observe(viewLifecycleOwner) {
            if (it) {
                // trigger next question
                viewModel.triggerNextQuestion.value = false
                // updating the question index)
                if (viewModel.checkAndIncrementQusIndex()) {
                    initQuiz()
                } else {
                    // reached the end
                    //trigger some thing
                    endQuiz()
                }
            }
        }

    }

    private fun setView() {
        // showing the loader
        binding.loadingPanel.fadeTo(true)
        binding.answerList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun initQuiz() {
        /*
         this will trigger the quiz
         */
        viewModel.updateTheQuestionCounter(binding.qusProgressTv)
        setQus()


    }

    private fun setQus() {

        val item = viewModel.getCurrentQuestion()

        if (item != null) {
            // setting related data
            viewModel.updateThePointTv(binding.pointTv)
            binding.qusTitle.text = item.question
            loadImage(binding.imageTV, item.questionImageUrl)
            //set Answer On the list
            val modifiedAnserList =
                viewModel.convertListToOwnAnswerList(item.answers, item.correctAnswer)
            if (modifiedAnserList != null) {

                mAdapter = AnswerAdapter(this, modifiedAnserList)
                // so performance of the list would be a issue for now
                setAdapter(mAdapter)

            }
            //setup timer
            setupTimerView(Const.ONGOING_QUESTION_TIME_LIMIT.toFloat())
            //starting the timer for quiz
            viewModel.createAndStartTimerForOngoingQuestion()
        }

    }

    private fun loadImage(imageTV: ImageView, questionImageUrl: String?) {
        if (questionImageUrl == null || questionImageUrl.toString().lowercase() == "null") {
            binding.imageTV.visibility = View.GONE
        } else {
            activity?.let {
                Glide.with(it)
                    .load(questionImageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(imageTV)
            }
            binding.imageTV.visibility = View.VISIBLE
        }


    }

    /*
    this method will load the quiz list when the fragment starts
     */
    private fun callForData() {
        //starting to observe the data coming from web in live data
        viewModel.qusResponse.observe(viewLifecycleOwner) {
            if (it.first != null) {
                initQuiz()
                binding.loadingPanel.fadeTo(false)
            } else {
                binding.loadingPanel.fadeTo(false)
                Toast.makeText(context, "Error : ${it.second?.message}", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun setAdapter(mAdapter: AnswerAdapter) {
        binding.answerList.adapter = mAdapter
    }

    override fun onItemSelected(position: Int, item: AnswerModel) {

        triggerAnswer(position, item)

    }

    private fun triggerAnswer(position: Int, item: AnswerModel) {
        // initialize the re-render fo the list
        val list = mAdapter.getList()
        list[position].checker = 1
        mAdapter =
            AnswerAdapter(this, list, showAnswer = true) // setting new adapter to show answer
        binding.answerList.adapter = mAdapter
        // calculate the score
        viewModel.calculateTheAnswer(item)
        // update point
        viewModel.updateTheUserPointTv(binding.scroeTv)
        //now check if its the last question or not
        if (viewModel.continueQuestion()) {
            triggerNextQuestion()
        } else {
            // end is here
            // so end the timer
            viewModel.cancelAllTimer()
            endQuiz()
        }


    }

    private fun endQuiz() {


        // trigger a dialogue to let user know all the question is ended
        MaterialAlertDialogBuilder(binding.root.context)
            .setTitle("Congratulation")
            .setMessage("Quiz Has Ended. Your Score is ${viewModel.totalUserScore}")
            .setPositiveButton(
                "OK"
            ) { dialogInterface, _ ->
                //add this score to the database
                viewModel.addScoreToDatabase()
                dialogInterface.dismiss()
                findNavController().navigate(
                    R.id.action_quizFragment_to_welcomeFragment, null, NavOptions.Builder()
                        .build()
                )
            }
            .setCancelable(false)
            .show()

    }

    private fun triggerNextQuestion() {
        //setting and changing the view
        setupTimerView(Const.NEXT_QUESTION_INTERVAL.toFloat())
        //creating the timer
        viewModel.createAndStartTimerForNextQuestion()
    }

    private fun setupTimerView(max: Float) {

        binding.circularProgressBar.progress = 0f
        binding.circularProgressBar.progressMax = max/1000
    }


}