package com.example.composition.domain.useCases

import com.example.composition.domain.entities.GameSettings
import com.example.composition.domain.entities.Level
import com.example.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository){
    operator fun invoke(level: Level) : GameSettings{
        return repository.getGameSettings(level)
    }
}