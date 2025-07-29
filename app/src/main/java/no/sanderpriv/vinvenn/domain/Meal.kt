package no.sanderpriv.vinvenn.domain

data class Meal(
    val id: String,
    val name: String,
)

data class MealsUiModel(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isRefreshing: Boolean = false,
    val meals: List<Meal> = emptyList(),
)
