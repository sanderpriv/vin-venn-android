package no.sanderpriv.vinvenn.ui.wines

import FailedView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import no.sanderpriv.vinvenn.domain.WinesUiModel
import no.sanderpriv.vinvenn.ui.common.LoadingView
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun FindWinesScreen(
    mealId: String,
    title: String,
    urlClick: (String) -> Unit,
    viewModel: WinesViewModel = koinViewModel(parameters = { parametersOf(mealId) }),
) {
    val winesResult by viewModel.winesResult.collectAsState()

    val currentWinesResult = winesResult
    when (currentWinesResult) {
        is WinesUiModel.Loading -> LoadingView()
        is WinesUiModel.Failed -> FailedView()
        is WinesUiModel.Success -> WinesView(
            title = title,
            wines = currentWinesResult.wines,
            urlClick = urlClick,
        )
    }
}
