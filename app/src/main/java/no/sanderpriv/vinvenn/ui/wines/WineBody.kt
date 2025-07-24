package no.sanderpriv.vinvenn.ui.wines

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import no.sanderpriv.vinvenn.R
import no.sanderpriv.vinvenn.domain.Wine
import no.sanderpriv.vinvenn.ui.common.PreviewModels
import no.sanderpriv.vinvenn.ui.theme.onSurface
import no.sanderpriv.vinvenn.ui.theme.primary
import no.sanderpriv.vinvenn.ui.theme.tertiary
import java.util.Locale

@Composable
fun WineBody(body: Wine.BodyInfo) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(body.imageUrl).crossfade(true).build(),
            contentDescription = null,
            modifier = Modifier.height(230.dp),
            loading = {
                Box(
                    modifier = Modifier.height(230.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = primary)
                }
            }
        )

        Row(
            modifier = Modifier
                .background(Color.Black, RoundedCornerShape(4.dp)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = String.format(Locale.getDefault(), "%.1f", body.score),
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Icon(
                Icons.Default.Star,
                null,
                tint = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        Box(
            modifier = Modifier.background(tertiary, RoundedCornerShape(8.dp))
        ) {
            val description = buildAnnotatedString {
                append("\"${body.description}\"")
                withStyle(
                    SpanStyle(fontWeight = FontWeight.Bold)
                ) {
                    append(" - Vinvenn")
                }
                appendInlineContent("icon")
            }

            val inlineContent = mapOf(
                Pair(
                    "icon",
                    InlineTextContent(
                        Placeholder(
                            width = 22.sp,
                            height = 22.sp,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                        )
                    ) {
                        Image(
                            painterResource(R.drawable.tapper),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 4.dp),
                        )
                    }
                )
            )

            Text(
                text = description,
                fontSize = 12.sp,
                color = onSurface,
                lineHeight = 22.sp,
                modifier = Modifier.padding(16.dp),
                inlineContent = inlineContent,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() = WineBody(PreviewModels.wine.bodyInfo)
