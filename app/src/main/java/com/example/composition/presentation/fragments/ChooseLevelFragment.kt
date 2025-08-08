package com.example.composition.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentChooseLevelBinding
import com.example.composition.domain.entities.Level

class ChooseLevelFragment : Fragment() {
    private var _binding : FragmentChooseLevelBinding? = null
    private val binding : FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLevelTest.setOnClickListener { launchLevelFragment(Level.TEST) }
        binding.buttonLevelEasy.setOnClickListener { launchLevelFragment(Level.EASY) }
        binding.buttonLevelNormal.setOnClickListener { launchLevelFragment(Level.MEDIUM) }
        binding.buttonLevelHard.setOnClickListener { launchLevelFragment(Level.HARD) }
    }

    private fun launchLevelFragment(level : Level){
        findNavController().navigate(ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}