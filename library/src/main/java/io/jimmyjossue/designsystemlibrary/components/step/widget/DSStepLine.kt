package io.jimmyjossue.designsystemlibrary.components.step.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension

@Composable
internal fun RowScope.DSStepLineVertical(
    isSuccess: Boolean = false,
    colorSuccess: Color = color.primary,
    colorDefault: Color = color.typography,
) {
    Box(
        modifier = Modifier
            .zIndex(1f)
            .weight(1f)
            .height(
                height = if (isSuccess) dimension.smalled * 2 else dimension.smalled
            )
            .background(
                color = if (isSuccess) colorSuccess else colorDefault
            )
    )
}

@Composable
internal fun ColumnScope.DSStepLineHorizontal(
    isSuccess: Boolean = false,
    colorSuccess: Color = color.primary,
    colorDefault: Color = color.typography,
) {
    Box(
        modifier = Modifier
            .zIndex(1f)
            .weight(1f)
            .width(
                width = if (isSuccess) dimension.smalled * 2 else dimension.smalled
            )
            .background(
                color = if (isSuccess) colorSuccess else colorDefault
            )
    )
}
