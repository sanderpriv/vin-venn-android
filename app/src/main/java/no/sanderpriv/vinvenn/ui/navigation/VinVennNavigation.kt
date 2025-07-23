package no.sanderpriv.vinvenn.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import no.sanderpriv.vinvenn.ui.meals.PickMealScreen

@Composable
fun VinVennNavigation(
    modifier: Modifier = Modifier,
    urlClick: (String) -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "pick_meal",
        modifier = modifier,
    ) {
        composable("pick_meal") {
            PickMealScreen(
                onMealClick = { searchString, title ->
                    navController.navigate("find_wines?searchString=$searchString&title=$title")
                }
            )
        }
        composable(
            route = "find_wines?searchString={searchString}&title={title}",
            arguments = listOf(
                navArgument("searchString") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
//            val searchString = backStackEntry.arguments?.getString("searchString")
//            val title = backStackEntry.arguments?.getString("title")
//            if (searchString == null || title == null) {
//                Text("oops!", color = onSurface)
//            } else {
//                FindWinesScreen(searchString, title, urlClick)
//            }
        }
    }
}
