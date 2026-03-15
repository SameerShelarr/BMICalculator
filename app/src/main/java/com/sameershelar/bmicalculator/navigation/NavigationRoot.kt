package com.sameershelar.bmicalculator.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.sameershelar.bmicalculator.ui.screens.HeightInputScreen
import com.sameershelar.bmicalculator.ui.screens.HomeScreen

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack =
        rememberNavBackStack(
            elements =
                arrayOf(
                    Route.HomeScreenRoute,
                ),
        )

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = { entry ->
            when (entry) {
                is Route.HomeScreenRoute -> {
                    NavEntry(entry) {
                        HomeScreen(
                            modifier = modifier,
                            onEditHeightClicked = {
                                backStack.add(
                                    element = Route.HeightInputScreenRoute,
                                )
                            },
                        )
                    }
                }

                is Route.HeightInputScreenRoute -> {
                    NavEntry(entry) {
                        HeightInputScreen(
                            modifier = modifier,
                            onForwardClick = {
                                backStack.add(
                                    element = Route.HomeScreenRoute,
                                )
                            },
                        )
                    }
                }

                else -> error("Unknown route: $entry")
            }
        },
    )
}
