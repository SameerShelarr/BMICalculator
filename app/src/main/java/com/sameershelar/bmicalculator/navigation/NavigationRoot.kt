package com.sameershelar.bmicalculator.navigation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.sameershelar.bmicalculator.ui.screens.HeightInputScreen
import com.sameershelar.bmicalculator.ui.screens.HomeScreen

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val activity = LocalActivity.current

    val backStack =
        rememberNavBackStack(
            elements =
                arrayOf(
                    Route.HomeScreenRoute,
                ),
        )

    var lastBackPressTime by remember { mutableLongStateOf(0L) }

    BackHandler(enabled = true) {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastBackPressTime < 2000) {
                activity?.finish()
            } else {
                lastBackPressTime = currentTime
                Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
            }
        }
    }

    NavDisplay(
        backStack = backStack,
        entryDecorators =
            listOf(
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
                                backStack.clear()
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
