package com.example.composition.domain.entities

data class Result(
    val winners : Boolean,
    val countOfRightAnswers : Int,
    val countAnswers : Int,
    val gameSettings: GameSettings
)
