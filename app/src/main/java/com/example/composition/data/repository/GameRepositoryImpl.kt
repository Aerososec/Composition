package com.example.composition.data.repository

import com.example.composition.domain.entities.GameSettings
import com.example.composition.domain.entities.Level
import com.example.composition.domain.entities.Question
import com.example.composition.domain.repository.GameRepository
import kotlin.math.max
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {
    private const val MIN_SUM_VALUE = 2
    private const val MIN_SUMMAND_VALUE = 1

    override fun getGameSettings(level: Level): GameSettings {
        return when(level){
            Level.TEST ->{
                GameSettings(
                    maxSum = 10,
                    minCountOfRightAnswers = 3,
                    minPercentOfRightAnswers = 50,
                    countOfOptions = 6,
                    seconds = 10
                )
            }
            Level.EASY -> {
                GameSettings(
                    maxSum = 15,
                    minCountOfRightAnswers = 10,
                    minPercentOfRightAnswers = 70,
                    countOfOptions = 6,
                    seconds = 60
                )
            }
            Level.MEDIUM -> {
                GameSettings(
                    maxSum = 40,
                    minCountOfRightAnswers = 20,
                    minPercentOfRightAnswers = 80,
                    countOfOptions = 8,
                    seconds = 55
                )
            }
            Level.HARD -> {
                GameSettings(
                    maxSum = 70,
                    minCountOfRightAnswers = 35,
                    minPercentOfRightAnswers = 90,
                    countOfOptions = 10,
                    seconds = 50
                )
            }
        }
    }

    override fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question {
        val visibleSum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleSummand = Random.nextInt(MIN_SUMMAND_VALUE, maxSumValue)
        val rightAnswer = visibleSum - visibleSummand
        val options = HashSet<Int>()
        options.add(rightAnswer)
        while (options.size < countOfOptions){
            options.add(Random.nextInt(MIN_SUMMAND_VALUE, visibleSum))
        }
        return Question(sumValue = visibleSum, visibleValue = visibleSummand, options = options.toList())
    }
}