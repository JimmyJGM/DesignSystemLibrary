package io.jimmyjossue.designsystemlibrary.template.form.model

import io.jimmyjossue.designsystemlibrary.components.input.DSInputIcon
import io.jimmyjossue.designsystemlibrary.components.input.config.DSInputImeAction
import io.jimmyjossue.designsystemlibrary.components.input.config.DSKeyboardType
import io.jimmyjossue.designsystemlibrary.components.selectors.chips.DSChip
import io.jimmyjossue.designsystemlibrary.utils.DSCapitalizationType
import io.jimmyjossue.designsystemlibrary.utils.DSHorizontal

// Input
// dropdown
// check
// radios
// choose (date, image, file)
// submit

sealed class DSFormElement(
    open val key: String,
    open val isVisible: Boolean,
) {
    data class LabelBody(
        override val key: String = "LabelBody",
        override val isVisible: Boolean = true,
        val value: String,
        val align: DSHorizontal = DSHorizontal.LEFT,
    ) : DSFormElement(
        key = key,
        isVisible = isVisible
    )

    data class LabelCaption(
        override val key: String = "LabelCaption",
        override val isVisible: Boolean = true,
        val value: String,
        val align: DSHorizontal = DSHorizontal.LEFT,
    ) : DSFormElement(
        key = key,
        isVisible = isVisible
    )

    data class InputText(
        override val key: String,
        val displayName: String,
        override val isVisible: Boolean = true,
        val value: String,
        val hint: String? = null,
        val label: String? = null,
        val helper: String? = null,
        val isEnabled: Boolean = true,
        val isSingleLine: Boolean = false,
        val maxLines: Int = Int.MAX_VALUE,
        val isReadOnly: Boolean = false,
        val leadingIcon: DSInputIcon? = null,
        val trailingIcon: DSInputIcon? = null,
        val imeAction: DSInputImeAction? = null,
        val keyboardType: DSKeyboardType = DSKeyboardType.Text,
        val capitalization: DSCapitalizationType = DSCapitalizationType.None,
    ) : DSFormElement(
        key = key,
        isVisible = isVisible
    )

    data class InputDropdown(
        override val key: String,
        val displayName: String,
        override val isVisible: Boolean = true,
        val value: String? = null,
        val options: List<String> = emptyList(),
        val hint: String? = null,
        val label: String? = null,
        val helper: String? = null,
        val isEnabled: Boolean = true,
    ): DSFormElement(
        key = key,
        isVisible = isVisible
    )

    data class InputChips(
        override val key: String,
        val displayName: String,
        override val isVisible: Boolean = true,
        val options: List<DSChip> = emptyList(),
        val label: String? = null,
        val helper: String? = null,
        val isEnabled: Boolean = true,
        val maxSelected: Int = Int.MAX_VALUE,
    ): DSFormElement(
        key = key,
        isVisible = isVisible
    )

    data class ToggleSwitch(
        override val key: String,
        val displayName: String,
        override val isVisible: Boolean = true,
        val isSelected: Boolean = false,
        val label: String? = null,
        val helper: String? = null,
        val isEnabled: Boolean = true,
    ) : DSFormElement(
        key = key,
        isVisible = isVisible
    )
}

