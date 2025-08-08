package com.example.composition.domain.useCases

import com.example.composition.domain.entities.Question
import com.example.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {
    operator fun invoke(maxSumValue: Int) : Question{
        return repository.generateQuestion(maxSumValue, 6)
    }
}