package com.rahat.pro_hero.codingtest.ui.welcomeScreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rahat.pro_hero.codingtest.R
import com.rahat.pro_hero.codingtest.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment(R.layout.fragment_welcome) {
    private lateinit var binding: FragmentWelcomeBinding
    private val viewModel: WelcomeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWelcomeBinding.bind(view)

        viewModel.userHighScore.observe(viewLifecycleOwner) {
            binding.scoreTV.text = "${it} Point"
        }


        binding.startBtn.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_quizFragment)
        }


    }
}

