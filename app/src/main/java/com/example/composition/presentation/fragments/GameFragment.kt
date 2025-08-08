package com.example.composition.presentation.fragments


import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entities.GameResult
import com.example.composition.domain.entities.GameSettings
import com.example.composition.domain.entities.Level
import com.example.composition.presentation.viewModel.GameViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.presentation.viewModel.GameViewModelFactory

class GameFragment : Fragment() {
    private var _binding : FragmentGameBinding? = null
    private val binding : FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is nil ^_^")
    val safeArgs by navArgs<GameFragmentArgs>()
    private lateinit var level : Level
    private val gameViewModel : GameViewModel by lazy {
        ViewModelProvider(
            this,
            GameViewModelFactory(level)
        )[GameViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        level = safeArgs.level
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameViewModel.startGame()
        initTimer()
        initQuestion()
        initSettings()
        upgradeProgressBar()
        finishGame()
        with(binding) {
            tvOption1.setOnClickListener { userAnswer(tvOption1.text.toString()) }
            tvOption2.setOnClickListener { userAnswer(tvOption2.text.toString()) }
            tvOption3.setOnClickListener { userAnswer(tvOption3.text.toString()) }
            tvOption4.setOnClickListener { userAnswer(tvOption4.text.toString()) }
            tvOption5.setOnClickListener { userAnswer(tvOption5.text.toString()) }
            tvOption6.setOnClickListener { userAnswer(tvOption6.text.toString()) }
        }

    }

    private fun upgradeProgressBar(){
        gameViewModel.liveDataPercent.observe(viewLifecycleOwner) {
            binding.progressBar.progress = it
        }
    }

    private fun initSettings(){
        gameViewModel.liveDataRightAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it.toString()
        }
    }

    private fun finishGame(){
        gameViewModel.liveDataFinish.observe(viewLifecycleOwner) {
            findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameFinishedFragment(it))
        }
    }

    private fun initQuestion(){
        gameViewModel.liveDataQuestion.observe(viewLifecycleOwner) {
            with(binding) {
                tvSum.text = it.sumValue.toString()
                tvLeftNumber.text = it.visibleValue.toString()
                val options = it.options.shuffled()
                tvOption1.text = options[0].toString()
                tvOption2.text = options[1].toString()
                tvOption3.text = options[2].toString()
                tvOption4.text = options[3].toString()
                tvOption5.text = options[4].toString()
                tvOption6.text = options[5].toString()
            }
        }
    }

    private fun initTimer(){
        gameViewModel.liveDataTimer.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
    }

    private fun userAnswer(value : String){
        gameViewModel.userAnswer(value.toInt())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}