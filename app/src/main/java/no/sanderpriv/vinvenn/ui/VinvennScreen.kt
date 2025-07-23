package no.sanderpriv.vinvenn.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import no.sanderpriv.vinvenn.ui.navigation.VinVennNavigation
import no.sanderpriv.vinvenn.ui.theme.background
import no.sanderpriv.vinvenn.ui.theme.primary

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun VinvennScreen(
    urlClick: (String) -> Unit,
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
        VinVennNavigation(modifier = Modifier.padding(innerPadding), urlClick)
    }
}
