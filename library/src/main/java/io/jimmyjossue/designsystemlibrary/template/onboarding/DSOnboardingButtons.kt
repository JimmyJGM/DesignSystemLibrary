package io.jimmyjossue.designsystemlibrary.template.onboarding

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonIcon
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonPrimary
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonSecondary
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonUtils
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacerFilled
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLower
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import io.jimmyjossue.designsystemlibrary.utils.DSHorizontal

@Composable
internal fun OnboardingButtons(
    pageCurrent: Int,
    pageLast: Int,
    contentColor: Color,
    backgroundColor: Color,
    onBackgroundColor: Color,
    textBtnFinish: String? = null,
    hasSkipButton: Boolean = true,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onFinish: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimension.semiLarge),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimension.medium)
    ) {
        if (pageCurrent < pageLast && hasSkipButton) {
            DSButtonPrimary(
                text = stringResource(id = R.string.ds_onboarding_btn_skip),
                onClick = onFinish,
                modifier = Modifier.height(53.dp),
                contentPadding = PaddingValues(horizontal = dimension.medium),
                shapeButton = shape.semiLarge,
                colors = DSButtonUtils.getButtonPrimaryColors(
                    content = contentColor.alphaHigh,
                    background = contentColor.alphaLower
                ),
            )
        }

        if (pageCurrent > 0) {
            DSButtonSecondary(
                onClick = onPrevious,
                shapeButton = shape.semiLarge,
                contentPadding = PaddingValues(dimension.medium, dimension.small),
                icon = DSButtonIcon(icon = R.drawable.ic_navigation_back),
                colors = DSButtonUtils.getButtonSecondaryColors(
                    background = backgroundColor.alphaLower,
                    border = Color.Transparent,
                    content = backgroundColor,
                )
            )
        }

        DSSpacerFilled()

        OnboardingButtonPrimary(
            backgroundColor = backgroundColor,
            onBackgroundColor = onBackgroundColor,
            isFinish = pageCurrent == pageLast,
            textBtnFinish = textBtnFinish,
            onNext = onNext,
            onFinish = onFinish,
        )
    }
}

@Composable
private fun OnboardingButtonPrimary(
    backgroundColor: Color,
    onBackgroundColor: Color,
    isFinish: Boolean = false,
    textBtnFinish: String? = null,
    onNext: () -> Unit,
    onFinish: () -> Unit,
) {
    DSButtonPrimary(
        modifier = Modifier
            .then(
                when (isFinish) {
                    true -> Modifier.fillMaxWidth()
                    false -> Modifier
                }
            )
            .height(height = 53.dp)
            .animateContentSize(),
        shapeButton = shape.semiLarge,
        contentPadding = PaddingValues(
            horizontal = dimension.medium.plus(dimension.small),
            vertical = dimension.smalled.times(7),
        ),
        colors = DSButtonUtils.getButtonPrimaryColors(
            background = backgroundColor,
            content = onBackgroundColor,
        ),
        icon = DSButtonIcon(
            icon = R.drawable.ic_navigation_next,
            position = DSHorizontal.RIGHT
        ).takeIf {
            isFinish.not()
        },
        text = when (isFinish) {
            true -> textBtnFinish ?: stringResource(id = R.string.ds_onboarding_btn_finish)
            false -> stringResource(id = R.string.ds_onboarding_btn_next)
        },
        onClick = when (isFinish) {
            true -> onFinish
            false -> onNext
        },
    )
}
