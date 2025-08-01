package com.example.composition.domain.entities

data class GameSettings(
    val maxSum : Int,
    val minCountOfRightAnswers : Int,
    val minPercentOfRightAnswers : Int,
    val countOfOptions : Int,
    val seconds : Int
)
