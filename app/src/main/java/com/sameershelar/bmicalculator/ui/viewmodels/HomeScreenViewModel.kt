package com.sameershelar.bmicalculator.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sameershelar.bmicalculator.data.BmiEntry
import com.sameershelar.bmicalculator.data.BmiRepository
import com.sameershelar.bmicalculator.data.DataStoreRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class HomeScreenViewModel(
    private val dataStoreRepository: DataStoreRepository,
    private val bmiRepository: BmiRepository,
) : ViewModel() {
    var height by mutableIntStateOf(150)
        private set

    var weightInput by mutableStateOf("")
        private set

    var isWeightError by mutableStateOf(false)
        private set

    val bmiHistory: StateFlow<List<BmiEntry>> =
        bmiRepository
            .getBmiHistory()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList(),
            )

    val latestBmi: StateFlow<Float?> =
        bmiHistory
            .map { it.firstOrNull()?.bmi }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null,
            )

    init {
        viewModelScope.launch {
            dataStoreRepository.getHeight().collect { savedHeight ->
                height = savedHeight
            }
        }
    }

    fun onWeightInputChange(weight: String) {
        val filteredWeight = weight.filter { it.isDigit() || it == '.' }
        val decimalCount = filteredWeight.count { it == '.' }
        if (decimalCount <= 1) {
            weightInput = filteredWeight
            isWeightError = false
        }
    }

    fun calculateBmi() {
        val weight = weightInput.toFloatOrNull()
        if (weight != null && weight > 0 && height > 0) {
            val heightInMeters = height / 100f
            val bmiValue = weight / (heightInMeters * heightInMeters)
            val roundedBmi = (bmiValue * 10).roundToInt() / 10f

            val newEntry =
                BmiEntry(
                    bmi = roundedBmi,
                    weight = weight,
                    height = height,
                )

            viewModelScope.launch {
                bmiRepository.insertBmiEntry(newEntry)
                weightInput = ""
                isWeightError = false
            }
        } else {
            isWeightError = true
        }
    }

    fun deleteBmiEntry(entry: BmiEntry) {
        viewModelScope.launch {
            bmiRepository.deleteBmiEntry(entry)
        }
    }

    fun insertBmiEntry(entry: BmiEntry) {
        viewModelScope.launch {
            bmiRepository.insertBmiEntry(entry)
        }
    }

    fun deleteAllHistory() {
        viewModelScope.launch {
            bmiRepository.deleteAllHistory()
        }
    }
}
