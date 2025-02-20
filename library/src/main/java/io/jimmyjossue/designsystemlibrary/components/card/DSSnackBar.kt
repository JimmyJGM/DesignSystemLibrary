package io.jimmyjossue.designsystemlibrary.components.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLower
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaSemiLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.utils.DSSizeType
import io.jimmyjossue.designsystemlibrary.utils.textdecorator.decoratedAnnotatedString

internal data class DSSnackBarVisuals(
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration,
    override val message: String,
    override val withDismissAction: Boolean = true,
    @DrawableRes val icon: Int? = null,
    val sizeType: DSSizeType = DSSizeType.NORMAL,
    val cardType: DSCardInfoType = DSCardInfoType.INFORMATION,
    val cardShape: Shape = RoundedCornerShape(8.dp),
    val onClick: (() -> Unit)? = null,
) : SnackbarVisuals

@Composable
internal fun screenSnackBar(hostState: SnackbarHostState?) = @Composable {
    SnackbarHost(hostState = hostState ?: remember { SnackbarHostState() }) {
        val lineCount = remember { mutableIntStateOf(1) }
        val visuals = it.visuals as DSSnackBarVisuals

        Snackbar(
            containerColor = color.surface.copy(alpha = 0.9f),
            contentColor = visuals.cardType.getContentColor(),
            shape = visuals.cardShape,
            modifier = Modifier
                .padding(horizontal = dimension.small)
                .padding(bottom = dimension.smalled)
                .fillMaxWidth()
                .clip(shape = visuals.cardShape)
                .border(
                    width = 1.dp,
                    color = visuals.cardType.getBorderColor().alphaLow,
                    shape = visuals.cardShape
                ),
        ) {
            if (visuals.message.isNotBlank()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimension.medium),
                    verticalAlignment = if (lineCount.intValue == 1) Alignment.CenterVertically else Alignment.Top
                ) {
                    if (visuals.icon != null) {
                        Icon(
                            modifier = Modifier.size(size = visuals.sizeType.getIconSize()),
                            painter = painterResource(id = visuals.icon),
                            tint = Color.Unspecified,
                            contentDescription = null,
                        )
                    }
                    Text(
                        modifier = Modifier.weight(1f),
                        text = visuals.message.decoratedAnnotatedString(),
                        style = visuals.sizeType.getTypography(),
                        color = color.typography,
                        onTextLayout = { textLayout ->
                            if (lineCount.intValue != textLayout.lineCount) {
                                lineCount.intValue != textLayout.lineCount
                            }
                        }
                    )
                }
            }
        }
    }
}
