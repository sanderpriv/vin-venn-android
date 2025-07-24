package no.sanderpriv.vinvenn.ui.wines

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.sanderpriv.vinvenn.domain.Wine
import no.sanderpriv.vinvenn.ui.common.PreviewModels
import no.sanderpriv.vinvenn.ui.common.screenWidth

@Composable
fun WinesView(
    title: String,
    wines: List<Wine>,
    urlClick: (String) -> Unit
) = Column(
    verticalArrangement = Arrangement.spacedBy(16.dp),
    modifier = Modifier.verticalScroll(rememberScrollState())
) {

    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = Modifier.padding(horizontal = 16.dp )
    )

    val groupedWines = wines.groupBy { it.recommendationOrder }

    groupedWines.forEach { group ->
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .height(intrinsicSize = IntrinsicSize.Max),
        ) {
            group.value.forEach { wine ->
                Card(
                    onClick = { urlClick(wine.header.url) },
                    colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                    modifier = Modifier.width(screenWidth() - 64.dp).fillMaxHeight().padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        WineHeader(wine.header)
                        WineBody(wine.bodyInfo)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() = WinesView(title = "Bringebær", wines = PreviewModels.wines, urlClick = {})
