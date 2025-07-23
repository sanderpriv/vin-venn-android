package no.sanderpriv.vinvenn.ui.meals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.sanderpriv.vinvenn.domain.MealsUiModel
import no.sanderpriv.vinvenn.repository.VinVennRepository

class MealsViewModel(
    private val vinVennRepository: VinVennRepository,
) : ViewModel() {

    private val _mealsUiModel = MutableStateFlow<MealsUiModel>(MealsUiModel.Loading)
    val mealsUiModel = _mealsUiModel.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        loadMeals()
    }

    fun refresh() {
        _isRefreshing.update { true }
        loadMeals()
        _isRefreshing.update { false }
    }

    fun loadMeals() {
        viewModelScope.launch { _mealsUiModel.update { getMeals() } }
    }

    private suspend fun getMeals(): MealsUiModel {
        val meals = vinVennRepository.getMeals().getOrNull()

        if (meals == null) return MealsUiModel.Failed

        return MealsUiModel.Success(meals)
    }
}