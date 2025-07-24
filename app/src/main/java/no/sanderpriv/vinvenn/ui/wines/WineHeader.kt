package no.sanderpriv.vinvenn.ui.wines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.sanderpriv.vinvenn.domain.Wine
import no.sanderpriv.vinvenn.ui.common.PreviewModels
import no.sanderpriv.vinvenn.ui.theme.secondary

@Composable
fun WineHeader(headerInfo: Wine.HeaderInfo) {
    Column {

        Text(text = headerInfo.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)

        Text(text = "${headerInfo.category.capitalize()} ${headerInfo.volume}")
        Text(text = "Fra ${headerInfo.origin}")

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End)
        ) {
            Text(text = "${headerInfo.price} kr hos Vinmonopolet", color = secondary)
            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = secondary)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() = WineHeader(PreviewModels.wine.header)

