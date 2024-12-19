package io.jimmyjossue.designsystemlibrary.components.loading

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacer
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.isLuminanceMore
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.dsAnimateAlphaState
import io.jimmyjossue.designsystemlibrary.utils.dsAnimateRotatedState
import io.jimmyjossue.designsystemlibrary.utils.onClick

private val pivotFraction = TransformOrigin(pivotFractionX = 0.55F, pivotFractionY = 0.57F)
private val progressStrokeWidth = 4.dp
private val progressSize = 42.dp
private val colorBlack = Color.Black
private val colorWhite = Color.White

@Composable
fun DSLoaderIndeterminateScreen(
    isVisible: Boolean = true,
    isEnableBackHandler: Boolean = true,
    text: String? = null,
    backgroundColor: Color = color.background,
    primaryColor: Color = color.primary,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Infinite")
    val rotateAnimated = infiniteTransition.dsAnimateRotatedState()
    val alphaAnimated = infiniteTransition.dsAnimateAlphaState()

    BackHandler(
        enabled = isEnableBackHandler.not(),
        onBack = onClick
    )

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor.alphaMedium)
                .clickable(enabled = false, onClick = onClick, role = Role.Image),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator(
                    color = primaryColor,
                    trackColor = Color.Transparent,
                    strokeWidth = progressStrokeWidth,
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .size(size = progressSize)
                        .graphicsLayer {
                            transformOrigin = pivotFraction
                            rotationZ = rotateAnimated.value
                        },
                )
                if (!text.isNullOrBlank()) {
                    DSSpacer(size = dimension.large)
                    Text(
                        color = if (backgroundColor.isLuminanceMore) colorBlack else colorWhite,
                        modifier = Modifier.alpha(alpha = alphaAnimated.value),
                        textAlign = TextAlign.Center,
                        style = typography.button,
                        text = text,
                    )
                }
            }
        }
    }
}
