package com.example.a4.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a4.data.FunFact
import com.example.a4.data.FunFactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FunFactViewModel(private val repository: FunFactRepository) : ViewModel() {

    private val _facts = MutableStateFlow<List<FunFact>>(emptyList())
    val facts: StateFlow<List<FunFact>> = _facts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allFacts.collect { factList ->
                _facts.value = factList
            }
        }
    }

    fun fetchNewFact() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                repository.fetchAndStoreFact()
            } catch (e: Exception) {
                _error.value = "Failed to fetch fact: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteAllFacts() {
        viewModelScope.launch {
            try {
                repository.deleteAllFacts()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to delete facts: ${e.message}"
            }
        }
    }
}

class FunFactViewModelFactory(private val repository: FunFactRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FunFactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FunFactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
