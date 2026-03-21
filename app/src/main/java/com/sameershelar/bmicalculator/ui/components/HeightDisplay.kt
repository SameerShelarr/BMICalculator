package com.sameershelar.bmicalculator.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sameershelar.bmicalculator.R

@Composable
fun HeightDisplay(
    height: Int,
    onEditHeightClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = "Height: ${height}cm",
            style = MaterialTheme.typography.bodyLarge,
        )
        Icon(
            painter = painterResource(R.drawable.edit),
            contentDescription = "Edit height",
            modifier =
                Modifier
                    .padding(start = 8.dp)
                    .clickable(
                        onClick = onEditHeightClicked,
                        indication = ripple(bounded = false, radius = 16.dp),
                        interactionSource = remember { MutableInteractionSource() },
                    ),
        )
    }
}
