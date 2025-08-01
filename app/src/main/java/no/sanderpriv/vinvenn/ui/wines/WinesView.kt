package no.sanderpriv.vinvenn.ui.wines

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.sanderpriv.vinvenn.domain.Wine
import no.sanderpriv.vinvenn.ui.common.PreviewModels
import no.sanderpriv.vinvenn.ui.common.screenWidth
import no.sanderpriv.vinvenn.ui.wines.toDp

@Composable
fun WinesView(
    wines: List<Wine>,
    urlClick: (String) -> Unit,
) = Column(
    verticalArrangement = Arrangement.spacedBy(16.dp),
    modifier = Modifier.verticalScroll(rememberScrollState())
) {

    val groupedWines = wines.groupBy { it.recommendationOrder }

    groupedWines.forEach { group ->

        var minHeight by rememberSaveable { mutableIntStateOf(0) }

        val listState = rememberLazyListState()
        LazyRow(
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(listState),
        ) {
            items(group.value) { wine ->
                Card(
                    modifier = Modifier
                        .width(screenWidth() - 64.dp)
                        .heightIn(min = minHeight.toDp())
                        .onSizeChanged {
                            val height = it.height
                            if (height > minHeight) minHeight = height
                        }
                        .padding(horizontal = 16.dp),
                    onClick = { urlClick(wine.header.url) },
                    colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
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

@Composable
fun Int.toDp() = with(LocalDensity.current) {
    this@toDp.toDp()
}

@Preview(showBackground = true)
@Composable
private fun Preview() = WinesView(wines = PreviewModels.wines, urlClick = {})
