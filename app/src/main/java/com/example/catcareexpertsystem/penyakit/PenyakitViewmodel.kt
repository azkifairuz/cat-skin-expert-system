package com.example.catcareexpertsystem.penyakit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PenyakitViewModel : ViewModel() {
    private val repository = PenyakitRepository()
    private val _data = MutableStateFlow<List<Penyakit>>(emptyList())
    val data: StateFlow<List<Penyakit>> get() = _data
    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            val result = repository.getData()
            _data.value = result
        }
    }

}