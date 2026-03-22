package com.sameershelar.bmicalculator.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CalculateBmiButton(
    onCalculateBmi: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onCalculateBmi,
        modifier = modifier,
    ) {
        Text(
            text = "Calculate BMI",
            style = MaterialTheme.typography.labelLarge,
        )
    }
}
