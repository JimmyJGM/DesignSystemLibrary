package io.jimmyjossue.designsystemlibrary.utils.extension

import androidx.compose.ui.graphics.Color

val Color?.isTransparentOrNull get() = this == Color.Transparent || this == null