package com.sameershelar.bmicalculator.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sameershelar.bmicalculator.data.BmiEntry

@Composable
fun HomeBmiHistorySection(
    bmiHistory: List<BmiEntry>,
    emptyStateMessage: String,
    onDeleteBmiEntry: (BmiEntry) -> Unit,
    onRequestClearAllHistory: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (bmiHistory.isNotEmpty()) {
        BmiHistoryList(
            bmiHistory = bmiHistory,
            onDeleteBmiEntry = onDeleteBmiEntry,
            onDeleteAllHistory = onRequestClearAllHistory,
            modifier = modifier,
        )
    } else {
        EmptyHistoryState(
            message = emptyStateMessage,
            modifier = modifier,
        )
    }
}
