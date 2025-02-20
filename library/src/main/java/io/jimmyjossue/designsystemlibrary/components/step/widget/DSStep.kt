package io.jimmyjossue.designsystemlibrary.components.step.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import io.jimmyjossue.designsystemlibrary.components.step.config.DSStepStateType
import io.jimmyjossue.designsystemlibrary.components.step.config.DSStepType
import io.jimmyjossue.designsystemlibrary.components.step.config.DSStepsColors
import io.jimmyjossue.designsystemlibrary.components.step.config.DSStepsConfig
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography

@Composable
internal fun DSStepVertical(
    title: String? = null,
    subTitle: String? = null,
    isLoading: Boolean = false,
    isLast: Boolean = false,
    state: DSStepStateType = DSStepStateType.Initial,
    type: DSStepType = DSStepType.Icon,
    colors: DSStepsColors = DSStepsConfig.getColors(),
) {
    val context = LocalContext.current
    val sizeStep = remember { mutableFloatStateOf(0f) }
    val screenPixelDensity = context.resources.displayMetrics.density

    Row(
        horizontalArrangement = Arrangement.spacedBy(dimension.medium),
        modifier = Modifier.heightIn(min = dimension.semiLarge),
    ) {
        Column(
            modifier = Modifier.width(dimension.semiLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DSStepIndicatorIcon(
                type = type,
                isLoading = isLoading,
                colorSuccess = colors.primary,
                colorOnSuccess = colors.onPrimary,
                colorDefault = colors.outline,
                isSuccess = state == DSStepStateType.Done,
            )
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .zIndex(1f)
                        .size(
                            height = (sizeStep.floatValue / screenPixelDensity).dp,
                            width = dimension.smalled
                        )
                        .background(
                            color = when (state == DSStepStateType.Done) {
                                true -> colors.primary
                                false -> colors.outline
                            }
                        )
                )
            }
        }
        Column(
            modifier = Modifier
                .onSizeChanged { sizeStep.floatValue = it.height.toFloat() },
        ) {
            Text(
                text = title.orEmpty(),
                style = typography.body,
                color = colors.typography.alphaHigh,
            )
            if (!subTitle.isNullOrBlank()) {
                Text(
                    text = subTitle,
                    style = typography.caption,
                    color = colors.typography.alphaMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 250)
@Composable
private fun PreviewDSStepVertical() {
    val stepType = remember { mutableStateOf<DSStepType>(DSStepType.Number(value = 1)) }
    stepType.value = DSStepType.Icon

    Column(
        modifier = Modifier.padding(dimension.small)
    ) {
        DSStepVertical(
            type = stepType.value,
            state = DSStepStateType.Done,
            title = "Primer paso",
            subTitle = "El primer paso es importante para este proceso."
        )
        DSStepVertical(
            type = stepType.value,
            state = DSStepStateType.Done,
            title = "Segundo paso",
            subTitle = "Probablemente no sea necesario."
        )
        DSStepVertical(
            type = stepType.value,
            state = DSStepStateType.Progress,
            title = "Tercer paso",
            subTitle = "Indispesable sin duda."
        )
        DSStepVertical(
            isLast = true,
            type = stepType.value,
            state = DSStepStateType.Initial,
            title = "Cuarto paso",
            subTitle = "El ultimo paso se debe de hacer con ayuda extra de la app."
        )
    }
}
