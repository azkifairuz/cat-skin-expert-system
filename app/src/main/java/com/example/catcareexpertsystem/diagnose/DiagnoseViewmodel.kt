package com.example.catcareexpertsystem.diagnose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catcareexpertsystem.penyakit.Penyakit
import com.example.catcareexpertsystem.penyakit.PenyakitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiagnoseViewmodel : ViewModel() {
    private val repository = GejalaRepository()
    private val repositoryPenyakit = PenyakitRepository()
    private val _quetion = MutableStateFlow<List<Gejala>>(emptyList())
    val quetion: StateFlow<List<Gejala>> = _quetion
    private  val _isFinish =  MutableStateFlow(false)
    val isFinish : StateFlow<Boolean> = _isFinish

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    private val _gejalaPenyakit = MutableStateFlow<List<GejalaPenyakit>>(emptyList())
    private val _penyakit = MutableStateFlow<List<Penyakit>>(emptyList())

    private val _results = MutableStateFlow<List<Result>>(emptyList())
    val results: StateFlow<List<Result>> = _results
    init {
        fetchQuestion()
        fetchPenyakit()
        fetchGejalaPenyakit()
    }

    private val _answer = MutableStateFlow<List<Answer>>(emptyList())
    val answer: StateFlow<List<Answer>> = _answer

    private fun fetchQuestion() {
        viewModelScope.launch {
            val result = repository.getData()
            _quetion.value = result
        }
    }

    private fun fetchGejalaPenyakit() {
        viewModelScope.launch {
            val result = repository.getGejalaPenyakit()
            _gejalaPenyakit.value = result
        }
    }

    private fun fetchPenyakit() {
        viewModelScope.launch {
            val result = repositoryPenyakit.getData()
            _penyakit.value = result
        }
    }
    fun answerQuestion(questionCode: String, cf: Double) {
        _answer.value += Answer(questionCode, cf)
        if (_currentQuestionIndex.value < _quetion.value.size - 1) {
            _currentQuestionIndex.value++
        }else{
            _currentQuestionIndex.value++
            calculateCF()
            _isFinish.value = true
        }
    }

    private fun calculateCF() {
        val answers = _answer.value
        val penyakitCFMap = mutableMapOf<String, Double>()

        for (answer in answers) {
            val gejalaPenyakit = _gejalaPenyakit.value.filter { it.gejalaCode == answer.gejalaCode }
            for (gp in gejalaPenyakit) {
                val cfCombine = penyakitCFMap[gp.penyakitCode]?.let { existingCF ->
                    combineCF(existingCF, gp.nilaiCf * answer.cf)
                } ?: (gp.nilaiCf * answer.cf)
                penyakitCFMap[gp.penyakitCode] = cfCombine
            }
        }

        val results = penyakitCFMap.map { (penyakitCode, cf) ->
            val penyakitName = _penyakit.value.find { it.penyakitCode == penyakitCode }?.penyakit ?: "Unknown"
            Result(penyakitName, cf * 100)
        }.sortedByDescending { it.cf }

        _results.value = results
    }

    private fun combineCF(cf1: Double, cf2: Double): Double {
        return cf1 + cf2 * (1 - cf1)
    }
}
