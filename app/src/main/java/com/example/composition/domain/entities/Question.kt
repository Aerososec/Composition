package com.example.composition.domain.entities

data class Question(
    val sumValue : Int,
    val visibleValue : Int,
    val options : List<Int>
){
    val rightAnswer = sumValue - visibleValue
}
