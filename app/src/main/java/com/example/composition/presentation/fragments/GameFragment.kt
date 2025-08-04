package com.example.composition.presentation.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.databinding.FragmentWelcomeBinding
import com.example.composition.domain.entities.GameResult
import com.example.composition.domain.entities.GameSettings
import com.example.composition.domain.entities.Level

class GameFragment : Fragment() {
    private var _binding : FragmentGameBinding? = null
    private val binding : FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is nil ^_^")

    private lateinit var level : Level

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        level = arguments?.getParcelable(LEVEL_KEY, Level::class.java) as Level
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvOption1.setOnClickListener {  }
        binding.tvOption2.setOnClickListener {  }
        binding.tvOption3.setOnClickListener {  }
        binding.tvOption4.setOnClickListener {  }
        binding.tvOption5.setOnClickListener {  }
        binding.tvOption6.setOnClickListener {  }

        val gameSettings = GameSettings(
            maxSum = 40,
            minCountOfRightAnswers = 20,
            minPercentOfRightAnswers = 80,
            countOfOptions = 8,
            seconds = 55
        )

        val result = GameResult(
            winners = true,
            countOfRightAnswers = 5,
            countAnswers = 10,
            gameSettings
        )

        binding.tvSum.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, GameFinishedFragment.newInstance(result))
                .addToBackStack(null)
                .commit()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val LEVEL_KEY = "level_key"

        fun newInstance(level : Level) : GameFragment{
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LEVEL_KEY, level)
                }
            }
        }
    }
}