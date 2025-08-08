package com.example.composition.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entities.GameResult


class GameFinishedFragment : Fragment() {
    private var _binding : FragmentGameFinishedBinding? = null
    private val binding : FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding is nil ^_^")

    private lateinit var gameResult : GameResult
    val safeArgs by navArgs<GameFinishedFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameResult = safeArgs.gameResult
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener { restartGame() }
        val requireAnswers = "Необходимое число правильных ответов ${gameResult.gameSettings.minCountOfRightAnswers}"
        val minPercent = "Необходимый процент правильных ответов ${gameResult.gameSettings.minPercentOfRightAnswers}"
        val rightPercent = "Процент правильных ответов: ${gameResult.countOfRightAnswers / gameResult.countAnswers * 100}"
        val scoreAnswers = "Ваш счет: ${gameResult.countOfRightAnswers}"
        binding.gameResult = safeArgs.gameResult
        with(binding) {
            tvScoreAnswers.text = scoreAnswers
            tvRequiredPercentage.text = minPercent
            tvScorePercentage.text = rightPercent
        }
    }

    private fun restartGame(){
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}