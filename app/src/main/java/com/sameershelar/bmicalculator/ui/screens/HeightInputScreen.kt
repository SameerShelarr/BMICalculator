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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameershelar.bmicalculator.R
import com.sameershelar.bmicalculator.ui.components.HeightPicker
import com.sameershelar.bmicalculator.ui.components.PickerStyle
import com.sameershelar.bmicalculator.ui.theme.BMICalculatorTheme
import com.sameershelar.bmicalculator.ui.viewmodels.HeightInputScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HeightInputScreen(
    modifier: Modifier = Modifier,
    viewModel: HeightInputScreenViewModel = koinViewModel(),
    onForwardClick: () -> Unit = {},
) {
    HeightInputScreenContent(
        modifier = modifier,
        height = viewModel.height,
        onHeightChange = viewModel::onHeightChange,
        onForwardClick = { viewModel.saveHeight(onForwardClick) },
    )
}

@Composable
fun HeightInputScreenContent(
    modifier: Modifier = Modifier,
    height: Int,
    onHeightChange: (Int) -> Unit,
    onForwardClick: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Text(
                text = "${height}cm",
                style =
                    MaterialTheme.typography.headlineLarge.copy(
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
                        initialHeight = height,
                        normalTypeLineColor = MaterialTheme.colorScheme.primary.toArgb(),
                        tenTypeLineColor = MaterialTheme.colorScheme.primary.toArgb(),
                        fiveTypeLineColor = MaterialTheme.colorScheme.primary.toArgb(),
                    ),
            ) {
                onHeightChange(it)
            }
        }

        Icon(
            painter = painterResource(R.drawable.arrow_forward),
            contentDescription = "Go to next",
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
                    .clickable(
                        onClick = onForwardClick,
                        indication = ripple(bounded = false, radius = 64.dp),
                        interactionSource = remember { MutableInteractionSource() },
                    ).size(48.dp),
        )
    }
}

@Preview(showBackground = true, device = "spec:width=800dp,height=480dp,dpi=440")
@Composable
fun HeightInputScreenLandscapePreview() {
    BMICalculatorTheme {
        Surface {
            HeightInputScreenContent(
                height = 175,
                onHeightChange = {},
                onForwardClick = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeightInputScreenPreview() {
    BMICalculatorTheme {
        Surface {
            HeightInputScreenContent(
                height = 175,
                onHeightChange = {},
                onForwardClick = {},
            )
        }
    }
}
