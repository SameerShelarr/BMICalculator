package com.sameershelar.bmicalculator.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sameershelar.bmicalculator.ui.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(),
) {
    Box(
        modifier =
            modifier
                .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Home Screen")
    }
}
