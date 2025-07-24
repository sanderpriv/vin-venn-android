import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.sanderpriv.vinvenn.ui.theme.primary
import no.sanderpriv.vinvenn.ui.theme.secondary
import no.sanderpriv.vinvenn.ui.theme.surface


@Composable
fun FailedView(
    errorMessage: String? = null,
    onRetry: (() -> Unit)? = null,
) = Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
) {
    Text(text = errorMessage ?: "Noe gikk galt :(", color = primary)
    onRetry?.let {
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = secondary,
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(text = "Prøv på nytt", color = surface)
        }
    }
}

@Preview
@Composable
private fun Preview() = FailedView(onRetry = {})