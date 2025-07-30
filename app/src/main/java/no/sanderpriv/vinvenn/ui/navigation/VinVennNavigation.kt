package no.sanderpriv.vinvenn.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import no.sanderpriv.vinvenn.ui.meals.PickMealScreen
import no.sanderpriv.vinvenn.ui.wines.FindWinesScreen

@Composable
fun VinVennNavigation(
    modifier: Modifier = Modifier,
    urlClick: (String) -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MainScreen.MealsScreen,
        modifier = modifier,
    ) {
        composable<MainScreen.MealsScreen> {
            PickMealScreen(
                onMealClick = { meal ->
                    navController.navigate(
                        MainScreen.FindWinesScreen(
                            mealId = meal.id,
                            title = meal.name,
                        )
                    )
                }
            )
        }

        composable<MainScreen.FindWinesScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<MainScreen.FindWinesScreen>()
            val mealId = args.mealId
            val title = args.title
            FindWinesScreen(
                mealId = mealId,
                title = title,
                urlClick = urlClick,
                onBackClick = {
                    val canGoBack =
                        navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
                    if (canGoBack) {
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}
