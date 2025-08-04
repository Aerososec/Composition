package com.example.composition.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class GameSettings(
    val maxSum : Int,
    val minCountOfRightAnswers : Int,
    val minPercentOfRightAnswers : Int,
    val countOfOptions : Int,
    val seconds : Int
) : Parcelable
