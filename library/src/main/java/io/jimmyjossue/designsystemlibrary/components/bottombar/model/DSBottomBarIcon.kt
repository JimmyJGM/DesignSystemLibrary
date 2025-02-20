package io.jimmyjossue.designsystemlibrary.components.bottombar.model

import androidx.annotation.DrawableRes

data class DSBottomBarIcon(
    val id: String,
    val text: String? = null,
    @DrawableRes val icon: Int,
    @DrawableRes val iconSelected: Int? = null,
    val withNotification: Boolean = false,
)

internal fun DSBottomBarIcon.getIcon(isSelected: Boolean) = when (iconSelected != null) {
    true -> if (isSelected) iconSelected else icon
    false -> icon
}
