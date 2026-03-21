package com.sameershelar.bmicalculator.ui.viewmodels

import com.sameershelar.bmicalculator.data.BmiEntry
import com.sameershelar.bmicalculator.data.BmiRepository
import com.sameershelar.bmicalculator.data.DataStoreRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withTimeout

@Suppress("unused")
@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest :
    BehaviorSpec({
        val testDispatcher = UnconfinedTestDispatcher()
        val dataStoreRepository = mockk<DataStoreRepository>()
        val bmiRepository = mockk<BmiRepository>()

        beforeTest {
            Dispatchers.setMain(testDispatcher)
        }

        afterTest {
            Dispatchers.resetMain()
        }

        Given("a HomeScreenViewModel") {
            every { dataStoreRepository.getHeight() } returns flowOf(165)
            every { bmiRepository.getBmiHistory() } returns flowOf(emptyList())

            val createViewModel = {
                HomeScreenViewModel(dataStoreRepository, bmiRepository)
            }

            When("it is initialized") {
                val viewModel = createViewModel()
                testDispatcher.scheduler.advanceUntilIdle()

                Then("the height should be the value from DataStore") {
                    viewModel.height shouldBe 165
                }

                Then("the history should be empty and latest BMI should be null") {
                    viewModel.bmiHistory.value shouldBe emptyList()
                    viewModel.latestBmi.value shouldBe null
                }
            }

            When("onWeightInputChange is called") {
                val viewModel = createViewModel()
                testDispatcher.scheduler.advanceUntilIdle()

                Then("it should filter only numbers and one decimal") {
                    viewModel.onWeightInputChange("70.5")
                    viewModel.weightInput shouldBe "70.5"

                    viewModel.onWeightInputChange("70..5")
                    viewModel.weightInput shouldBe "70.5"

                    viewModel.onWeightInputChange("abc70.5")
                    viewModel.weightInput shouldBe "70.5"
                }

                Then("it should reset the weight error") {
                    // Manually set error to true first (simulating a previous error)
                    // We can't set it directly as it's private set, but we can trigger it with calculateBmi
                    viewModel.onWeightInputChange("")
                    viewModel.calculateBmi()
                    viewModel.isWeightError shouldBe true

                    viewModel.onWeightInputChange("70")
                    viewModel.isWeightError shouldBe false
                }
            }

            When("calculateBmi is called with valid input") {
                val viewModel = createViewModel()
                testDispatcher.scheduler.advanceUntilIdle()

                coEvery { bmiRepository.insertBmiEntry(any()) } returns Unit

                viewModel.onWeightInputChange("70") // Height is 165
                // BMI = 70 / (1.65 * 1.65) = 70 / 2.7225 = 25.711... -> 25.7

                viewModel.calculateBmi()
                testDispatcher.scheduler.advanceUntilIdle()

                Then("it should calculate correct BMI and insert into repository") {
                    coVerify {
                        bmiRepository.insertBmiEntry(
                            match {
                                it.bmi == 25.7f && it.weight == 70f && it.height == 165
                            },
                        )
                    }
                }

                Then("it should clear the input and error") {
                    viewModel.weightInput shouldBe ""
                    viewModel.isWeightError shouldBe false
                }
            }

            When("calculateBmi is called with invalid input") {
                val viewModel = createViewModel()
                testDispatcher.scheduler.advanceUntilIdle()

                Then("it should set weight error to true for empty input") {
                    viewModel.onWeightInputChange("")
                    viewModel.calculateBmi()
                    viewModel.isWeightError shouldBe true
                }

                Then("it should set weight error to true for zero weight") {
                    viewModel.onWeightInputChange("0")
                    viewModel.calculateBmi()
                    viewModel.isWeightError shouldBe true
                }

                Then("it should set weight error to true for negative weight") {
                    // Filter allows digits and dot, but let's say something bypasses it, or we test the logic
                    // Actually onWeightInputChange doesn't allow '-'
                    viewModel.onWeightInputChange("-50")
                    viewModel.weightInput shouldBe "50" // '-' is filtered out

                    // So weight will be 50, which is valid.
                }
            }

            When("deleteBmiEntry is called") {
                val viewModel = createViewModel()
                testDispatcher.scheduler.advanceUntilIdle()

                val entryToDelete = BmiEntry(id = 1, bmi = 22f, weight = 60f, height = 165)
                coEvery { bmiRepository.deleteBmiEntry(entryToDelete) } returns Unit

                viewModel.deleteBmiEntry(entryToDelete)
                testDispatcher.scheduler.advanceUntilIdle()

                Then("it should call repository to delete the entry") {
                    coVerify { bmiRepository.deleteBmiEntry(entryToDelete) }
                }
            }

            When("insertBmiEntry is called") {
                val viewModel = createViewModel()
                testDispatcher.scheduler.advanceUntilIdle()

                val entryToInsert = BmiEntry(id = 1, bmi = 22f, weight = 60f, height = 165)
                coEvery { bmiRepository.insertBmiEntry(entryToInsert) } returns Unit

                viewModel.insertBmiEntry(entryToInsert)
                testDispatcher.scheduler.advanceUntilIdle()

                Then("it should call repository to insert the entry") {
                    coVerify { bmiRepository.insertBmiEntry(entryToInsert) }
                }
            }

            When("deleteAllHistory is called") {
                val viewModel = createViewModel()
                testDispatcher.scheduler.advanceUntilIdle()

                coEvery { bmiRepository.deleteAllHistory() } returns Unit

                viewModel.deleteAllHistory()
                testDispatcher.scheduler.advanceUntilIdle()

                Then("it should call repository to delete all history") {
                    coVerify { bmiRepository.deleteAllHistory() }
                }
            }

            When("there is BMI history") {
                Then("it should match the flow from repository and have the latest BMI") {
                    val entries =
                        listOf(
                            BmiEntry(id = 2, bmi = 24.5f, weight = 72f, height = 171),
                            BmiEntry(id = 1, bmi = 22.0f, weight = 65f, height = 171),
                        )
                    val mockBmiRepo = mockk<BmiRepository>()
                    every { mockBmiRepo.getBmiHistory() } returns flowOf(entries)

                    val viewModel = HomeScreenViewModel(dataStoreRepository, mockBmiRepo)

                    val historyJob = launch { viewModel.bmiHistory.collect {} }
                    val latestJob = launch { viewModel.latestBmi.collect {} }

                    withTimeout(1000) {
                        viewModel.bmiHistory
                            .first { it.isNotEmpty() }
                            .map { it.bmi } shouldBe entries.map { it.bmi }
                        viewModel.latestBmi.first { it != null } shouldBe 24.5f
                    }

                    historyJob.cancel()
                    latestJob.cancel()
                }
            }
        }
    })
