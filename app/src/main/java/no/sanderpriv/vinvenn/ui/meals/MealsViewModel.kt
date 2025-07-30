package no.sanderpriv.vinvenn.ui.meals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.sanderpriv.vinvenn.domain.MealsUiModel
import no.sanderpriv.vinvenn.repository.VinVennRepository
import kotlin.time.Duration.Companion.seconds

class MealsViewModel(
    private val vinVennRepository: VinVennRepository,
) : ViewModel() {

    private val _mealsUiModel = MutableStateFlow(MealsUiModel())
    val mealsUiModel = _mealsUiModel.asStateFlow()

    init {
        loadMeals()
    }

    fun refresh() {
        _mealsUiModel.update { it.copy(isRefreshing = true) }
        loadMeals()
        viewModelScope.launch {
            delay(0.5.seconds) // Ugly delay to let refresh animation, should be fixed in later material 3 versions
            _mealsUiModel.update { it.copy(isRefreshing = false) }
        }
    }

    fun loadMeals() {
        viewModelScope.launch {
            val meals = vinVennRepository.getMeals().getOrNull()
            if (meals.isNullOrEmpty()) {
                _mealsUiModel.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                    )
                }
                return@launch
            }

            _mealsUiModel.update {
                it.copy(
                    isLoading = false,
                    isError = false,
                    meals = meals,
                )
            }
        }
    }
}