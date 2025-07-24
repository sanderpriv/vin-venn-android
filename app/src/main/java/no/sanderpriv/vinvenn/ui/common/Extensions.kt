package no.sanderpriv.vinvenn.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun screenWidth() = LocalConfiguration.current.screenWidthDp.dp
