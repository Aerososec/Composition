package com.example.composition.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composition.domain.entities.Level

class GameViewModelFactory(val level: Level) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)){
            return GameViewModel(level) as T
        }
        else
            throw RuntimeException("Unknown ViewModel")
    }
}