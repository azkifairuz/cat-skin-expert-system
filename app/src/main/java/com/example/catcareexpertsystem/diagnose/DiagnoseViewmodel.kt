package com.example.catcareexpertsystem.diagnose

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catcareexpertsystem.history.DiagnosesHistory
import com.example.catcareexpertsystem.penyakit.Penyakit
import com.example.catcareexpertsystem.penyakit.PenyakitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        val penyakitCFMap = mutableMapOf<String, MutableList<Double>>()

        for (answer in answers) {
            val gejalaPenyakit = _gejalaPenyakit.value.filter { it.gejalaCode == answer.gejalaCode }
            for (gp in gejalaPenyakit) {
                val cfValue = gp.nilaiCf * answer.cf
                if (penyakitCFMap.containsKey(gp.penyakitCode)) {
                    penyakitCFMap[gp.penyakitCode]?.add(cfValue)
                } else {
                    penyakitCFMap[gp.penyakitCode] = mutableListOf(cfValue)
                }
            }
        }

        val results = penyakitCFMap.map { (penyakitCode, cfList) ->
            val penyakitName = _penyakit.value.find { it.penyakitCode == penyakitCode }?.penyakit ?: "Unknown"
            val combinedCF = combineCFSequentially(cfList)
            Result(penyakitName, combinedCF * 100)
        }.sortedByDescending { it.cf }

        _results.value = results
    }

    private fun combineCFSequentially(cfList: List<Double>): Double {
        if (cfList.isEmpty()) return 0.0
        var combinedCF = cfList[0]
        for (i in 1 until cfList.size) {
            combinedCF = combineCF(combinedCF, cfList[i])
        }
        return combinedCF
    }

    private fun combineCF(cf1: Double, cf2: Double): Double {
        return cf1 + cf2 * (1 - cf1)
    }

    fun clearResults() {
        _answer.value = emptyList()
        _results.value = emptyList()
        _currentQuestionIndex.value = 0
    }

    @SuppressLint("MutatingSharedPrefs")
    fun saveDiagnoseResult(context: Context, petName: String) {
        val highestResult = _results.value.maxByOrNull { it.cf }
        val diagnoseResult = highestResult?.penyakit ?: "Unknown"
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        // Save to SharedPreferences
        val sharedPreferences = context.getSharedPreferences("DiagnoseHistory", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val newEntry = "$petName|$diagnoseResult|$date"
        val existingEntries = sharedPreferences.getStringSet("history", mutableSetOf()) ?: mutableSetOf()
        existingEntries.add(newEntry)
        editor.putStringSet("history", existingEntries)
        editor.apply()
    }

    fun getDiagnoseHistory(context: Context): List<DiagnosesHistory> {
        val sharedPreferences = context.getSharedPreferences("DiagnoseHistory", Context.MODE_PRIVATE)
        val historySet = sharedPreferences.getStringSet("history", mutableSetOf()) ?: mutableSetOf()
        return historySet.map { entry ->
            val parts = entry.split("|")
            DiagnosesHistory(parts[0], parts[1], parts[2])
        }
    }
}
