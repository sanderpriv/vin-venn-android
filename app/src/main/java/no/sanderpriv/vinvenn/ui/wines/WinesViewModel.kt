package no.sanderpriv.vinvenn.ui.wines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.sanderpriv.vinvenn.domain.WinesUiModel
import no.sanderpriv.vinvenn.repository.VinVennRepository

class WinesViewModel(
    searchString: String,
    private val vinVennRepository: VinVennRepository,
) : ViewModel() {

    private val _winesUiResult = MutableStateFlow<WinesUiModel>(WinesUiModel.Loading)
    val winesResult = _winesUiResult.asStateFlow()

    init {
        viewModelScope.launch {
            viewModelScope.launch { _winesUiResult.update { getWines(searchString) } }
        }
    }

    private suspend fun getWines(searchString: String): WinesUiModel {
        val wines = vinVennRepository.findWines(searchString).getOrNull()

        if (wines == null) return WinesUiModel.Failed

        return WinesUiModel.Success(wines)
    }
}
