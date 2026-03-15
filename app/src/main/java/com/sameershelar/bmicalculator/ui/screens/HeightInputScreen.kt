package com.sameershelar.bmicalculator.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameershelar.bmicalculator.R
import com.sameershelar.bmicalculator.ui.components.HeightPicker
import com.sameershelar.bmicalculator.ui.components.PickerStyle
import com.sameershelar.bmicalculator.ui.viewmodels.HeightInputScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HeightInputScreen(
    modifier: Modifier = Modifier,
    viewModel: HeightInputScreenViewModel = koinViewModel(),
    onForwardClick: () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Text(
                text = "${viewModel.height}cm",
                style =
                    TextStyle(
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                modifier =
                    Modifier
                        .align(Alignment.CenterHorizontally),
            )

            HeightPicker(
                pickerStyle =
                    PickerStyle(
                        normalTypeLineColor = MaterialTheme.colorScheme.primary.toArgb(),
                        tenTypeLineColor = MaterialTheme.colorScheme.primary.toArgb(),
                        fiveTypeLineColor = MaterialTheme.colorScheme.primary.toArgb(),
                        initialHeight = viewModel.height,
                    ),
            ) {
                viewModel.onHeightChange(it)
            }
        }

        Icon(
            painter = painterResource(R.drawable.arrow_forward),
            contentDescription = "Go to next",
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .clickable(
                        onClick = {
                            viewModel.saveHeight(onForwardClick)
                        },
                        indication = ripple(bounded = false, radius = 64.dp),
                        interactionSource = remember { MutableInteractionSource() },
                    ).size(64.dp),
        )
    }
}
