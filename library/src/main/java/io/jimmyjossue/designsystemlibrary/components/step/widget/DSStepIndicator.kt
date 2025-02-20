package io.jimmyjossue.designsystemlibrary.components.step.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.loading.DSLoaderFadeBox
import io.jimmyjossue.designsystemlibrary.components.step.config.DSStepStateType
import io.jimmyjossue.designsystemlibrary.components.step.config.DSStepType
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.bold
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography

@Composable
internal fun DSStepIndicatorIcon(
    type: DSStepType = DSStepType.Icon,
    isLoading: Boolean = false,
    isSuccess: Boolean = false,
    colorSuccess: Color = color.primary,
    colorOnSuccess: Color = color.onPrimary,
    colorDefault: Color = color.typography,
) {
    if (!isLoading) {
        IndicatorIcon(
            type = type,
            isSuccess = isSuccess,
            colorSuccess = colorSuccess,
            colorOnSuccess = colorOnSuccess,
            colorDefault = colorDefault,
        )
    } else {
        DSLoaderFadeBox(size = 24.dp)
    }
}

@Composable
private fun IndicatorIcon(
    type: DSStepType = DSStepType.Icon,
    isSuccess: Boolean = false,
    colorSuccess: Color = color.primary,
    colorOnSuccess: Color = color.onPrimary,
    colorDefault: Color = color.typography,
) {
    if (isSuccess) {
        IndicatorSuccess(
            colorSuccess = colorSuccess,
            colorOnSuccess = colorOnSuccess,
            type = type,
        )
    } else {
        IndicatorDefault(
            colorDefault = colorDefault,
            type = type,
        )
    }
}

@Composable
private fun IndicatorSuccess(
    type: DSStepType = DSStepType.Icon,
    colorSuccess: Color = color.primary,
    colorOnSuccess: Color = color.onPrimary,
) {
    when (type) {
        is DSStepType.Icon -> Icon(
            modifier = Modifier
                .zIndex(10f)
                .size(size = dimension.semiLarge - dimension.smalled)
                .background(color = colorSuccess, shape = CircleShape)
                .padding(dimension.smalled),
            painter = painterResource(id = R.drawable.ds_ic_action_success),
            tint = colorOnSuccess,
            contentDescription = null,
        )
        is DSStepType.Number -> Box(
            contentAlignment = Center,
            modifier = Modifier
                .zIndex(10f)
                .size(size = dimension.semiLarge - dimension.smalled)
                .background(color = colorSuccess, shape = CircleShape)
        ) {
            Text(
                text = type.value.toString(),
                color = colorOnSuccess,
                style = typography.body.bold,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
internal fun IndicatorIcon(
    state: DSStepStateType = DSStepStateType.Initial,
    colorSuccess: Color = color.primary,
    colorOnSuccess: Color = color.onPrimary,
    colorDefault: Color = color.typography,
) {
    when (state) {
        DSStepStateType.Initial -> IndicatorIconPending(color = colorDefault)
        DSStepStateType.Progress -> IndicatorIconPending(color = colorSuccess) {
            Box(
                modifier = Modifier
                    .size(dimension.semiLarge / 2)
                    .background(color = colorSuccess.alphaHigh, shape = CircleShape)
            )
        }
        DSStepStateType.Done -> {
            Icon(
                painter = painterResource(id = R.drawable.ds_ic_action_success),
                contentDescription = null,
                tint = colorOnSuccess,
                modifier = Modifier
                    .zIndex(10f)
                    .size(size = dimension.semiLarge - dimension.smalled)
                    .background(color = colorSuccess, shape = CircleShape)
                    .padding(dimension.smalled),
            )
        }
    }
}

@Composable
private fun IndicatorIconPending(
    color: Color,
    size: Dp = dimension.semiLarge - dimension.smalled,
    content: @Composable (BoxScope.() -> Unit) = { },
) {
    Box(
        content = content,
        contentAlignment = Center,
        modifier = Modifier
            .zIndex(10f)
            .size(size = size)
            .border(
                color = color,
                width = dimension.smalled,
                shape = CircleShape
            ),
    )
}

@Composable
private fun IndicatorDefault(
    type: DSStepType = DSStepType.Icon,
    colorDefault: Color = color.typography,
) {
    when (type) {
        is DSStepType.Icon -> {
            Box(
                modifier = Modifier
                    .zIndex(10f)
                    .size(dimension.semiLarge - dimension.smalled)
                    .border(
                        width = dimension.smalled,
                        color = colorDefault,
                        shape = CircleShape
                    )
            )
        }
        is DSStepType.Number -> {
            Box(
                contentAlignment = Center,
                modifier = Modifier
                    .zIndex(10f)
                    .size(dimension.semiLarge - dimension.smalled)
                    .border(
                        width = dimension.smalled,
                        color = colorDefault,
                        shape = CircleShape
                    )
            ) {
                Text(
                    text = type.value.toString(),
                    color = colorDefault.alphaHigh,
                    style = typography.caption,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
