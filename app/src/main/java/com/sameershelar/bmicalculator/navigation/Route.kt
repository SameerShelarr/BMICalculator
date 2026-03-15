package com.sameershelar.bmicalculator.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object HeightInputScreenRoute : Route

    @Serializable
    data object HomeScreenRoute : Route
}
