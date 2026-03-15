package com.sameershelar.bmicalculator.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sameershelar.bmicalculator.data.DataStoreRepository
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    var height by mutableIntStateOf(150)
        private set

    init {
        viewModelScope.launch {
            dataStoreRepository.getHeight().collect { savedHeight ->
                height = savedHeight
            }
        }
    }
}
