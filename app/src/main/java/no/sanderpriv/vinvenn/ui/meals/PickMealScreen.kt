package no.sanderpriv.vinvenn.ui.meals

import FailedView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import no.sanderpriv.vinvenn.ui.theme.primary
import no.sanderpriv.vinvenn.ui.theme.surface
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickMealScreen(
    onMealClick: (meal: Meal) -> Unit,
    viewModel: MealsViewModel = koinViewModel(),
) {
    val mealsResult by viewModel.mealsUiModel.collectAsStateWithLifecycle()

    PickMealsView(
        mealsResult = mealsResult,
        onRefresh = viewModel::refresh,
        onRetry = viewModel::loadMeals,
        onMealClick = onMealClick,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PickMealsView(
    mealsResult: MealsUiModel,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    onMealClick: (Meal) -> Unit,
) {
    Scaffold(
        containerColor = background,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = background),
                title = {
                    Text(text = "Vinvenn", color = primary)
                }
            )
        }
    ) { innerPadding ->

        var query by rememberSaveable { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(background),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SearchBar(
                windowInsets = WindowInsets(0, 0, 0, 0),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(8.dp),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = query,
                        onQueryChange = { query = it },
                        onSearch = { /* TODO */ },
                        expanded = true,
                        onExpandedChange = { },
                        placeholder = {
                            Text(
                                text = "Søk",
                            )
                        },
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                IconButton(onClick = { query = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Tøm"
                                    )
                                }
                            }
                        }
                    )
                },
                expanded = false,
                onExpandedChange = { },
            ) {
            }

            PullToRefreshBox(
                isRefreshing = mealsResult.isRefreshing,
                onRefresh = onRefresh,
            ) {

                when {
                    mealsResult.isLoading -> LoadingView()
                    mealsResult.isError -> FailedView(onRetry = onRetry)
                    else -> MealsView(mealsResult.meals, onMealClick)
                }
            }
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
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = meal.name,
                    fontSize = 14.sp,
                    color = onSurface,
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = null,
                    tint = onSurface,
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() = VinvennTheme {
    PickMealsView(
        mealsResult = MealsUiModel(
            isLoading = false, meals = listOf(
                Meal("id", "Aioli"),
                Meal("id", "Aioli"),
                Meal("id", "Aioli"),
                Meal("id", "Aioli"),
                Meal("id", "Aioli"),
                Meal("id", "Aioli"),
                Meal("id", "Aioli"),
                Meal("id", "Aioli"),
            )
        ), onRefresh = {}, onRetry = {}, onMealClick = {})
}
