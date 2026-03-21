package com.sameershelar.bmicalculator.ui.viewmodels

import com.sameershelar.bmicalculator.data.DataStoreRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class HeightInputScreenViewModelTest :
    BehaviorSpec({
        val testDispatcher = StandardTestDispatcher()
        val dataStoreRepository = mockk<DataStoreRepository>()

        beforeTest {
            Dispatchers.setMain(testDispatcher)
        }

        afterTest {
            Dispatchers.resetMain()
        }

        Given("a HeightInputScreenViewModel") {
            every { dataStoreRepository.getHeight() } returns flowOf(170)

            val viewModel = HeightInputScreenViewModel(dataStoreRepository)
            testDispatcher.scheduler.advanceUntilIdle()

            When("it is initialized") {
                Then("the height should be the value from DataStore") {
                    viewModel.height shouldBe 170
                }
            }

            When("onHeightChange is called") {
                viewModel.onHeightChange(180)

                Then("the height should be updated") {
                    viewModel.height shouldBe 180
                }
            }

            When("saveHeight is called") {
                coEvery { dataStoreRepository.saveHeight(any()) } returns Unit
                var onSavedCalled = false

                viewModel.onHeightChange(190)
                viewModel.saveHeight { onSavedCalled = true }
                testDispatcher.scheduler.advanceUntilIdle()

                Then("it should save the height to DataStore") {
                    coVerify { dataStoreRepository.saveHeight(190) }
                }

                Then("it should call onSaved callback") {
                    onSavedCalled shouldBe true
                }
            }
        }
    })
