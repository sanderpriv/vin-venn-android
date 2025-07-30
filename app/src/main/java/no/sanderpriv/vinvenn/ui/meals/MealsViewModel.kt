package no.sanderpriv.vinvenn.ui.meals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.sanderpriv.vinvenn.domain.Meal
import no.sanderpriv.vinvenn.repository.VinVennRepository
import kotlin.time.Duration.Companion.seconds

class MealsViewModel(
    private val vinVennRepository: VinVennRepository,
) : ViewModel() {

    private val _allMeals = MutableStateFlow<List<Meal>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    val meals = combine(
        _allMeals,
        _searchQuery
    ) { allMeals, currentQuery ->
        val filteredMeals = if (currentQuery.isEmpty()) {
            allMeals
        } else {
            allMeals.filter { meal ->
                meal.name.contains(currentQuery, ignoreCase = true)
            }
        }

        filteredMeals
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList<Meal>()
    )


    init {
        loadMeals()
    }

    fun onSearch(searchQuery: String) = _searchQuery.update { searchQuery }

    fun refresh() {
        _isRefreshing.update { true }
        loadMeals()
        viewModelScope.launch {
            delay(0.5.seconds) // Ugly delay to let refresh animation, should be fixed in later material 3 versions
            _isRefreshing.update { false }
        }
    }

    fun loadMeals() {
        viewModelScope.launch {
            val meals = vinVennRepository.getMeals().getOrNull()
            _isLoading.update { false }
            if (meals.isNullOrEmpty()) {
                _isError.update { true }
                return@launch
            }

            _isError.update { false }
            _allMeals.update { meals }
        }
    }
}