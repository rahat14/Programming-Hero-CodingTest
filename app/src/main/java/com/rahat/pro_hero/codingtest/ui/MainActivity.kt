package com.rahat.pro_hero.codingtest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rahat.pro_hero.codingtest.R
import com.rahat.pro_hero.codingtest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onBackPressed() {

        val navigationController = findNavController(R.id.container_fragment)
        when (navigationController.currentDestination?.id) {
            R.id.quizFragment -> {
                triggerWarnDialog(navigationController)
            }

            else -> {
                super.onBackPressed()
            }
        }

    }

    private fun triggerWarnDialog(navigationController: NavController) {
        MaterialAlertDialogBuilder(binding.root.context)
            .setTitle("Alert")
            .setMessage("If You Leave The Quiz. Your Existing Score Wil Not Be Saved.")
            .setPositiveButton(
                "Ok"
            ) { dialogInterface, _ ->
                //add this score to the database
                dialogInterface.dismiss()

                navigationController.navigate(
                    R.id.action_quizFragment_to_welcomeFragment, null, NavOptions.Builder()
                        .build()
                )
                navigationController.popBackStack(R.id.quizFragment, true)
            }
            .setNegativeButton(
                "Cancel"
            ) { dialogInterface, _ ->
                //add this score to the database
                dialogInterface.dismiss()

            }

            .setCancelable(false)
            .show()
    }

}