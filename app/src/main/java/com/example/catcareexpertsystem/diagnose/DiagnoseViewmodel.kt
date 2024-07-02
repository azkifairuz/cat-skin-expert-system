package com.example.catcareexpertsystem.diagnose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catcareexpertsystem.penyakit.Penyakit
import com.example.catcareexpertsystem.penyakit.PenyakitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiagnoseViewmodel : ViewModel() {
    private val repository = PenyakitRepository()
    private val _quetion = MutableStateFlow<List<Penyakit>>(emptyList())
    val quetion: StateFlow<List<Penyakit>> = _quetion

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    init {
        fetchQuestion()
    }

    private val _answer = MutableStateFlow<List<Answer>>(emptyList())
    val answer: StateFlow<List<Answer>> = _answer

    private fun fetchQuestion() {
        viewModelScope.launch {
            val result = repository.getData()
            _quetion.value = result
        }
    }

    fun answerQuestion(questionCode: String, cf: Double) {
        _answer.value += Answer(questionCode, cf)
        if (_currentQuestionIndex.value < _quetion.value.size - 1) {
            _currentQuestionIndex.value++
        }else{
            _currentQuestionIndex.value++
        }
    }
}