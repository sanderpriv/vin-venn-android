package no.sanderpriv.vinvenn.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

val background = Color(0xFFfdeece)
val surface = Color(0xFFfefce8)
val onSurface = Color(0xFF000000)
val primary = Color(0xFF78716c)
val secondary = Color(0xFF854d0e)
val tertiary = Color(0xFFf5f5f4)

@Composable
fun VinvennTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider() {
        content()
    }
}
