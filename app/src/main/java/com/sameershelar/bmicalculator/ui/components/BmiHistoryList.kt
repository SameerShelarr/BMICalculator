package com.sameershelar.bmicalculator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sameershelar.bmicalculator.R
import com.sameershelar.bmicalculator.data.BmiEntry

@Composable
fun BmiHistoryList(
    bmiHistory: List<BmiEntry>,
    onDeleteBmiEntry: (BmiEntry) -> Unit,
    onDeleteAllHistory: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider()

        Spacer(modifier = Modifier.height(8.dp))

        // History Title and Delete All Button
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "History",
                style = MaterialTheme.typography.titleLarge,
            )
            IconButton(onClick = onDeleteAllHistory) {
                Icon(
                    painter = painterResource(R.drawable.delete_history),
                    contentDescription = "Delete all history",
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = bmiHistory,
                key = { it.id },
            ) { entry ->
                BmiHistoryItem(
                    entry = entry,
                    onDeleteClick = { onDeleteBmiEntry(entry) },
                )
            }
        }
    }
}
