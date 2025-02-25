package io.jimmyjossue.designsystemlibrary.components.button.model

import androidx.annotation.DrawableRes
import io.jimmyjossue.designsystemlibrary.utils.DSHorizontal

data class DSButtonIcon(
    @DrawableRes val icon: Int,
    val withTint: Boolean = true,
    val position: DSHorizontal = DSHorizontal.LEFT,
)