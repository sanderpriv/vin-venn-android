package no.sanderpriv.vinvenn.ui.wines

import FailedView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import no.sanderpriv.vinvenn.domain.WinesUiModel
import no.sanderpriv.vinvenn.ui.common.LoadingView
import no.sanderpriv.vinvenn.ui.common.PreviewModels
import no.sanderpriv.vinvenn.ui.theme.background
import no.sanderpriv.vinvenn.ui.theme.primary
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindWinesScreen(
    mealId: String,
    title: String,
    urlClick: (String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: WinesViewModel = koinViewModel(parameters = { parametersOf(mealId) }),
) {
    val winesResult by viewModel.winesResult.collectAsState()
    FindWinesView(
        winesResult = winesResult,
        title = title,
        urlClick = urlClick,
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FindWinesView(
    winesResult: WinesUiModel,
    title: String,
    urlClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        containerColor = background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = background),
                title = {
                    Text(text = title, color = primary)
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Gå tilbake",
                            tint = primary,
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(
            color = background,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {

            val currentWinesResult = winesResult
            when (currentWinesResult) {
                is WinesUiModel.Loading -> LoadingView()
                is WinesUiModel.Failed -> FailedView()
                is WinesUiModel.Success -> WinesView(
                    wines = currentWinesResult.wines,
                    urlClick = urlClick,
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() = FindWinesView(
    winesResult = WinesUiModel.Success(PreviewModels.wines),
    title = "Laks",
    urlClick = {},
    onBackClick = {},
)
