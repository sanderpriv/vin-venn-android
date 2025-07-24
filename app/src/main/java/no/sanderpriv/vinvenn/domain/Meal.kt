package no.sanderpriv.vinvenn.domain

data class Meal(
    val id: String,
    val name: String,
)

sealed interface MealsUiModel {
    data object Loading : MealsUiModel
    data object Failed : MealsUiModel
    data class Success(val meals: List<Meal>) : MealsUiModel
}
