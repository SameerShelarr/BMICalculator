package com.sameershelar.bmicalculator.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sameershelar.bmicalculator.data.DataStoreRepository
import kotlinx.coroutines.launch

class HeightInputScreenViewModel(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    var height by mutableIntStateOf(165)
        private set

    init {
        viewModelScope.launch {
            dataStoreRepository.getHeight().collect { savedHeight ->
                height = savedHeight
            }
        }
    }

    fun onHeightChange(newHeight: Int) {
        height = newHeight
    }

    fun saveHeight(onSaved: () -> Unit = {}) {
        viewModelScope.launch {
            dataStoreRepository.saveHeight(height)
            onSaved()
        }
    }
}
