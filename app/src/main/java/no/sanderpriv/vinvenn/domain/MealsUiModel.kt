package no.sanderpriv.vinvenn.domain

sealed interface MealsUiModel {
    data object Loading : MealsUiModel
    data object Failed : MealsUiModel
    data class Success(val meals: List<Meal>): MealsUiModel
}
