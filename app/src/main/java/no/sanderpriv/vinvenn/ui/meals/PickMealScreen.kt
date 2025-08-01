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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.sanderpriv.vinvenn.domain.Meal
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
    val meals by viewModel.meals.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val isError by viewModel.isError.collectAsStateWithLifecycle()
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()

    PickMealsView(
        isLoading = isLoading,
        isRefreshing = isRefreshing,
        isError = isError,
        meals = meals,
        searchQuery = query,
        onSearch = viewModel::onSearch,
        onRefresh = viewModel::refresh,
        onRetry = viewModel::loadMeals,
        onMealClick = onMealClick,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PickMealsView(
    isLoading: Boolean,
    isRefreshing: Boolean,
    isError: Boolean,
    meals: List<Meal>,
    searchQuery: String,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    onSearch: (String) -> Unit,
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
                        query = searchQuery,
                        onQueryChange = { onSearch(it) },
                        onSearch = { },
                        expanded = true,
                        onExpandedChange = { },
                        placeholder = {
                            Text(
                                text = "Søk",
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { onSearch("") }) {
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
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
            ) {

                when {
                    isLoading -> LoadingView()
                    isError -> FailedView(onRetry = onRetry)
                    else -> MealsView(meals, onMealClick)
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
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
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
        isError = false,
        isLoading = false,
        isRefreshing = true,
        meals = listOf(
            Meal("id", "Aioli"),
            Meal("id", "Aioli"),
            Meal("id", "Aioli"),
            Meal("id", "Aioli"),
            Meal("id", "Aioli"),
            Meal("id", "Aioli"),
            Meal("id", "Aioli"),
            Meal("id", "Aioli"),
        ),
        searchQuery = "Sushi",
        onSearch = {},
        onRefresh = {},
        onRetry = {},
        onMealClick = {},
    )
}
