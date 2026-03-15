package com.sameershelar.bmicalculator.ui.viewmodels

import com.sameershelar.bmicalculator.data.DataStoreRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest :
    BehaviorSpec({
        val testDispatcher = StandardTestDispatcher()
        val dataStoreRepository = mockk<DataStoreRepository>()

        beforeTest {
            Dispatchers.setMain(testDispatcher)
        }

        afterTest {
            Dispatchers.resetMain()
        }

        Given("a HomeScreenViewModel") {
            every { dataStoreRepository.getHeight() } returns flowOf(165)

            val viewModel = HomeScreenViewModel(dataStoreRepository)
            testDispatcher.scheduler.advanceUntilIdle()

            When("it is initialized") {
                Then("the height should be the value from DataStore") {
                    viewModel.height shouldBe 165
                }
            }
        }
    })
