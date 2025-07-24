package no.sanderpriv.vinvenn.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainScreen {

    @Serializable
    data object MealsScreen : MainScreen

    @Serializable
    data class FindWinesScreen(
        val mealId: String,
        val title: String,
    ) : MainScreen
}
