package no.sanderpriv.vinvenn

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import no.sanderpriv.vinvenn.ui.navigation.VinVennNavigation
import no.sanderpriv.vinvenn.ui.theme.VinvennTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )
        super.onCreate(savedInstanceState)
        setContent {
            VinvennTheme {
                VinVennNavigation(urlClick = ::onUrlClick)
            }
        }
    }

    private fun onUrlClick(url: String) {
        val uri = url.toUri()
        CustomTabsIntent.Builder().build().launchUrl(this, uri)
    }
}