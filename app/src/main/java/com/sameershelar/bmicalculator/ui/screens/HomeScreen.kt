package com.sameershelar.bmicalculator.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sameershelar.bmicalculator.R
import com.sameershelar.bmicalculator.ui.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(),
    onEditHeightClicked: () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row {
            Text(text = "Home Screen")
            Icon(
                painter = painterResource(R.drawable.edit),
                contentDescription = "Go to next",
                modifier =
                    Modifier
                        .padding(8.dp)
                        .clickable(
                            onClick = {
                                onEditHeightClicked.invoke()
                            },
                            indication = ripple(bounded = false, radius = 16.dp),
                            interactionSource = remember { MutableInteractionSource() },
                        ).align(Alignment.CenterVertically),
            )
        }
    }
}
