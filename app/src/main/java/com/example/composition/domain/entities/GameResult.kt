package com.example.composition.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val winners : Boolean,
    val countOfRightAnswers : Int,
    val countAnswers : Int,
    val gameSettings: GameSettings
) : Parcelable
