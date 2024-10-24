package io.jimmyjossue.designsystem.components.separator

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystem.theme.color

@Composable
fun DSDividerHorizontal(
    modifier: Modifier = Modifier,
    lineColor: Color = color.surfaceDark,
    thickness: Dp = 1.dp,
) {
    HorizontalDivider(
        color = lineColor,
        thickness = thickness,
        modifier = modifier
    )
}

@Composable
fun DSDividerVertical(
    modifier: Modifier = Modifier,
    lineColor: Color = color.surfaceDark,
    thickness: Dp = 1.dp,
) {
    VerticalDivider(
        color = lineColor,
        thickness = thickness,
        modifier = modifier
    )
}