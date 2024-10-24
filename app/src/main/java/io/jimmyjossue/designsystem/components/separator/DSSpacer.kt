package io.jimmyjossue.designsystem.components.separator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun DSSpacer(size: Dp) {
    Spacer(modifier = Modifier.size(size = size))
}

@Composable
fun RowScope.DSSpacerFilled(weight: Float = 1f) {
    Spacer(modifier = Modifier.weight(weight = weight))
}

@Composable
fun ColumnScope.DSSpacerFilled(weight: Float = 1f) {
    Spacer(modifier = Modifier.weight(weight = weight))
}
