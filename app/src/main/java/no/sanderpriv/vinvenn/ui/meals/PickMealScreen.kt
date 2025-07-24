package no.sanderpriv.vinvenn.ui.meals

import FailedView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.sanderpriv.vinvenn.domain.Meal
import no.sanderpriv.vinvenn.domain.MealsUiModel
import no.sanderpriv.vinvenn.ui.common.LoadingView
import no.sanderpriv.vinvenn.ui.theme.VinvennTheme
import no.sanderpriv.vinvenn.ui.theme.background
import no.sanderpriv.vinvenn.ui.theme.onSurface
import no.sanderpriv.vinvenn.ui.theme.surface
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickMealScreen(
    onMealClick: (meal: Meal) -> Unit,
    viewModel: MealsViewModel = koinViewModel(),
) {
    val mealsResult by viewModel.mealsUiModel.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = viewModel::refresh,
    ) {
        val currentMealsResult = mealsResult
        when (currentMealsResult) {
            is MealsUiModel.Failed -> FailedView(onRetry = viewModel::loadMeals)
            is MealsUiModel.Loading -> LoadingView()
            is MealsUiModel.Success -> MealsView(currentMealsResult.meals, onMealClick)
        }
    }
}

@Composable
fun MealsView(
    meals: List<Meal>,
    onMealClick: (meal: Meal) -> Unit,
) = LazyColumn(
    verticalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(vertical = 8.dp),
    modifier = Modifier.background(background)
) {
    items(meals) { meal ->
        Card(
            elevation = CardDefaults.elevatedCardElevation(),
            colors = CardDefaults.elevatedCardColors(containerColor = surface),
            onClick = { onMealClick(meal) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = meal.name,
                modifier = Modifier.padding(16.dp),
                fontSize = 14.sp,
                color = onSurface
            )
        }
    }
}

@Preview
@Composable
private fun Preview() = VinvennTheme {
    MealsView(
        meals = listOf(
            Meal("Aioli", "")
        ), onMealClick = { }
    )
}
