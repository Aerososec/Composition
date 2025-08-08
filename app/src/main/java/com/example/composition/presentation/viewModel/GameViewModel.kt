package com.example.composition.presentation.viewModel

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composition.R
import com.example.composition.data.repository.GameRepositoryImpl
import com.example.composition.domain.entities.GameResult
import com.example.composition.domain.entities.GameSettings
import com.example.composition.domain.entities.Level
import com.example.composition.domain.entities.Question
import com.example.composition.domain.useCases.GenerateQuestionUseCase
import com.example.composition.domain.useCases.GetGameSettingsUseCase

class GameViewModel(private val level: Level) : ViewModel() {
    private val repository = GameRepositoryImpl
    private var rightAnswers = 0
    private var timer : CountDownTimer? = null
    private var percentsOfRightAnswers = 0
    private var countAnswers = 0

    private lateinit var gameSettings: GameSettings
    private lateinit var activeQuestion : Question
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private val _liveDataGameSettings = MutableLiveData<GameSettings>()
    val liveDataGameSettings : LiveData<GameSettings>
        get() = _liveDataGameSettings

    private val _liveDataTimer = MutableLiveData<String>()
    val liveDataTimer : LiveData<String>
        get() = _liveDataTimer

    private val _liveDataQuestion = MutableLiveData<Question>()
    val liveDataQuestion : LiveData<Question>
        get() = _liveDataQuestion

    private val _liveDataRightAnswers = MutableLiveData<String>()
    val liveDataRightAnswers : LiveData<String>
        get() = _liveDataRightAnswers

    private val _liveDataPercent = MutableLiveData<Int>()
    val liveDataPercent : LiveData<Int>
        get() = _liveDataPercent

    private val _liveDataCountAnswers = MutableLiveData<Int>()
    val liveDataCountAnswers : LiveData<Int>
        get() = _liveDataCountAnswers

    private val _liveDataMinPercent = MutableLiveData<Int>()
    val liveDataMinPercent : LiveData<Int>
        get() = _liveDataMinPercent

    private val _liveDataFinish = MutableLiveData<GameResult>()
    val liveDataFinish : LiveData<GameResult>
        get() = _liveDataFinish

    fun startGame(){
        getGameSettings(level)
        _liveDataMinPercent.postValue(gameSettings.minPercentOfRightAnswers)
        rightAnswerInformation()
        startTimer()
        getQuestion()
    }

    fun getGameSettings(level : Level){
        gameSettings = getGameSettingsUseCase(level)
        _liveDataGameSettings.postValue(gameSettings)
    }

    fun getQuestion(){
        activeQuestion = generateQuestionUseCase(gameSettings.maxSum)
        _liveDataQuestion.postValue(activeQuestion)
    }

    fun userAnswer(value : Int){
        if (checkAnswer(value)){
            rightAnswers++
            rightAnswerInformation()
        }
        countAnswers++
        getPercentOfRightAnswer()
        getQuestion()
    }

    private fun getPercentOfRightAnswer(){
        percentsOfRightAnswers = rightAnswers * 100 / gameSettings.minCountOfRightAnswers
        _liveDataPercent.postValue(percentsOfRightAnswers)
    }

    private fun rightAnswerInformation(){
        _liveDataRightAnswers.postValue("Количество правильных ответов ${rightAnswers} (минимум ${gameSettings.minCountOfRightAnswers})")
    }

    fun checkAnswer(value: Int) : Boolean{
        return (value == (activeQuestion.sumValue - activeQuestion.visibleValue))
    }

    fun returnVisibleSum() : Int{
        return activeQuestion.sumValue
    }

    fun returnVisibleValue() : Int{
        return activeQuestion.visibleValue
    }

    fun returnOptions() : List<Int>{
        return activeQuestion.options
    }

    private fun startTimer(){
        timer = object : CountDownTimer(
            gameSettings.seconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ){
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / MILLIS_IN_SECONDS
                _liveDataTimer.postValue("0:${seconds}")
            }

            override fun onFinish() {
                finishGame()
            }

        }
        timer?.start()
    }

    private fun finishGame(){
        _liveDataFinish.postValue( GameResult(
            rightAnswers >= gameSettings.minCountOfRightAnswers
                    && percentsOfRightAnswers >= gameSettings.minPercentOfRightAnswers,
            rightAnswers,
            countAnswers,
            gameSettings
        ))
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object{
        private const val MILLIS_IN_SECONDS = 1000L
    }
}