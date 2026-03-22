package com.sameershelar.bmicalculator.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sameershelar.bmicalculator.data.BmiEntry
import com.sameershelar.bmicalculator.ui.components.BmiCard
import com.sameershelar.bmicalculator.ui.components.BmiHistoryList
import com.sameershelar.bmicalculator.ui.components.ClearHistoryDialog
import com.sameershelar.bmicalculator.ui.components.EmptyHistoryState
import com.sameershelar.bmicalculator.ui.components.HeightDisplay
import com.sameershelar.bmicalculator.ui.components.WeightInput
import com.sameershelar.bmicalculator.ui.theme.BMICalculatorTheme
import com.sameershelar.bmicalculator.ui.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = koinViewModel(),
    onEditHeightClicked: () -> Unit = {},
) {
    val bmiHistory by viewModel.bmiHistory.collectAsStateWithLifecycle()
    val latestBmi by viewModel.latestBmi.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    HomeScreenContent(
        modifier = modifier,
        height = viewModel.height,
        weightInput = viewModel.weightInput,
        isWeightError = viewModel.isWeightError,
        latestBmi = latestBmi,
        bmiHistory = bmiHistory,
        onWeightInputChange = viewModel::onWeightInputChange,
        onCalculateBmi = viewModel::calculateBmi,
        onDeleteBmiEntry = { entry ->
            viewModel.deleteBmiEntry(entry)
            scope.launch {
                val result =
                    snackbarHostState.showSnackbar(
                        message = "BMI entry deleted",
                        actionLabel = "Undo",
                        duration = SnackbarDuration.Short,
                    )
                if (result == SnackbarResult.ActionPerformed) {
                    viewModel.insertBmiEntry(entry)
                }
            }
        },
        onDeleteAllHistory = viewModel::deleteAllHistory,
        onEditHeightClicked = onEditHeightClicked,
        snackbarHostState = snackbarHostState,
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    height: Int,
    weightInput: String,
    isWeightError: Boolean,
    latestBmi: Float?,
    bmiHistory: List<BmiEntry>,
    onWeightInputChange: (String) -> Unit,
    onCalculateBmi: () -> Unit,
    onDeleteBmiEntry: (BmiEntry) -> Unit,
    onDeleteAllHistory: () -> Unit,
    onEditHeightClicked: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val emptyMessages =
        remember {
            listOf(
                "Your journey to a healthier you starts with a single calculation!",
                "Ready to see your progress? Enter your weight and let's go!",
                "BMI history is looking a bit lonely. Time for a check-in?",
                "Tracking your BMI is a great way to stay on top of your fitness goals.",
                "Know your numbers, own your health. Calculate your BMI now!",
            )
        }
    val randomMessage = remember(bmiHistory.isEmpty()) { emptyMessages.random() }

    if (showDeleteDialog) {
        @Suppress("AssignedValueIsNeverRead")
        ClearHistoryDialog(
            onConfirm = {
                onDeleteAllHistory()
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false },
        )
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        if (isLandscape) {
            Row(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
            ) {
                // Left column: Inputs and Latest BMI
                Column(
                    modifier =
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    HeightDisplay(
                        height = height,
                        onEditHeightClicked = onEditHeightClicked,
                    )

                    BmiCard(
                        bmi = latestBmi,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )

                    WeightInput(
                        weightInput = weightInput,
                        onWeightInputChange = onWeightInputChange,
                        isError = isWeightError,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onCalculateBmi,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "Calculate BMI",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }

                Spacer(modifier = Modifier.width(24.dp))

                // Right column: History
                Column(
                    modifier =
                        Modifier
                            .weight(1.2f)
                            .fillMaxHeight()
                            .padding(vertical = 16.dp),
                ) {
                    if (bmiHistory.isNotEmpty()) {
                        @Suppress("AssignedValueIsNeverRead")
                        BmiHistoryList(
                            bmiHistory = bmiHistory,
                            onDeleteBmiEntry = onDeleteBmiEntry,
                            onDeleteAllHistory = { showDeleteDialog = true },
                            modifier = Modifier.weight(1f),
                        )
                    } else {
                        EmptyHistoryState(
                            message = randomMessage,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        } else {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Height Display & Edit
                HeightDisplay(
                    height = height,
                    onEditHeightClicked = onEditHeightClicked,
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                // Latest BMI Card
                BmiCard(
                    bmi = latestBmi,
                    modifier = Modifier.padding(vertical = 16.dp),
                )

                // Weight Input
                WeightInput(
                    weightInput = weightInput,
                    onWeightInputChange = onWeightInputChange,
                    isError = isWeightError,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onCalculateBmi,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Calculate BMI",
                        style = MaterialTheme.typography.labelLarge,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (bmiHistory.isNotEmpty()) {
                    @Suppress("AssignedValueIsNeverRead")
                    BmiHistoryList(
                        bmiHistory = bmiHistory,
                        onDeleteBmiEntry = onDeleteBmiEntry,
                        onDeleteAllHistory = { showDeleteDialog = true },
                        modifier = Modifier.weight(1f),
                    )
                } else {
                    EmptyHistoryState(
                        message = randomMessage,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenEmptyPreview() {
    BMICalculatorTheme {
        Surface {
            HomeScreenContent(
                height = 175,
                weightInput = "",
                isWeightError = false,
                latestBmi = null,
                bmiHistory = emptyList(),
                onWeightInputChange = {},
                onCalculateBmi = {},
                onDeleteBmiEntry = {},
                onDeleteAllHistory = {},
                onEditHeightClicked = {},
            )
        }
    }
}

@Preview(showBackground = true, device = "spec:width=800dp,height=480dp,dpi=440")
@Composable
fun HomeScreenLandscapePreview() {
    BMICalculatorTheme {
        Surface {
            HomeScreenContent(
                height = 175,
                weightInput = "70",
                isWeightError = false,
                latestBmi = 22.9f,
                bmiHistory =
                    listOf(
                        BmiEntry(id = 0, bmi = 22.9f, weight = 70f, height = 175),
                        BmiEntry(id = 1, bmi = 24.5f, weight = 75f, height = 175),
                    ),
                onWeightInputChange = {},
                onCalculateBmi = {},
                onDeleteBmiEntry = {},
                onDeleteAllHistory = {},
                onEditHeightClicked = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BMICalculatorTheme {
        Surface {
            HomeScreenContent(
                height = 175,
                weightInput = "70",
                isWeightError = false,
                latestBmi = 22.9f,
                bmiHistory =
                    listOf(
                        BmiEntry(id = 0, bmi = 22.9f, weight = 70f, height = 175),
                        BmiEntry(id = 1, bmi = 24.5f, weight = 75f, height = 175),
                    ),
                onWeightInputChange = {},
                onCalculateBmi = {},
                onDeleteBmiEntry = {},
                onDeleteAllHistory = {},
                onEditHeightClicked = {},
            )
        }
    }
}
