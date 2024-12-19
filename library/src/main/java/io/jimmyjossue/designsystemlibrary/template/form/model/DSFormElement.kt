package io.jimmyjossue.designsystemlibrary.template.form.model

import io.jimmyjossue.designsystemlibrary.components.input.DSInputIcon
import io.jimmyjossue.designsystemlibrary.components.input.config.DSInputImeAction
import io.jimmyjossue.designsystemlibrary.components.input.config.DSKeyboardType
import io.jimmyjossue.designsystemlibrary.components.selectors.chips.DSChip
import io.jimmyjossue.designsystemlibrary.utils.DSCapitalization
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
        val value: String,
        val align: DSHorizontal = DSHorizontal.LEFT,
        override val key: String = "LabelBody",
        override val isVisible: Boolean = true,
    ) : DSFormElement(
        key = key,
        isVisible = isVisible
    )

    data class LabelCaption(
        val value: String,
        val align: DSHorizontal = DSHorizontal.LEFT,
        override val key: String = "LabelCaption",
        override val isVisible: Boolean = true,
    ) : DSFormElement(
        key = key,
        isVisible = isVisible
    )

    data class InputText(
        val value: String,
        val hint: String? = null,
        val label: String? = null,
        val helper: String? = null,
        val isEnabled: Boolean = true,
        val isSingleLine: Boolean = false,
        val maxLines: Int = Int.MAX_VALUE,
        val isReadOnly: Boolean = false,
        val displayName: String,
        val leadingIcon: DSInputIcon? = null,
        val trailingIcon: DSInputIcon? = null,
        val imeAction: DSInputImeAction? = null,
        val keyboardType: DSKeyboardType = DSKeyboardType.Text,
        val capitalization: DSCapitalization = DSCapitalization.None,
        override val key: String,
        override val isVisible: Boolean = true,
    ) : DSFormElement(
        key = key,
        isVisible = isVisible
    )

    data class InputDropdown(
        val value: String? = null,
        val options: List<String> = emptyList(),
        val hint: String? = null,
        val label: String? = null,
        val helper: String? = null,
        val isEnabled: Boolean = true,
        val displayName: String,
        override val key: String,
        override val isVisible: Boolean = true,
    ): DSFormElement(
        key = key,
        isVisible = isVisible
    )

    data class InputChips(
        val options: List<DSChip> = emptyList(),
        val label: String? = null,
        val helper: String? = null,
        val isEnabled: Boolean = true,
        val maxSelected: Int = Int.MAX_VALUE,
        val displayName: String,
        override val key: String,
        override val isVisible: Boolean = true,
    ): DSFormElement(
        key = key,
        isVisible = isVisible
    )

    data class ToggleSwitch(
        val isSelected: Boolean = false,
        val label: String? = null,
        val helper: String? = null,
        val isEnabled: Boolean = true,
        val displayName: String,
        override val key: String,
        override val isVisible: Boolean = true,
    ) : DSFormElement(
        key = key,
        isVisible = isVisible
    )
}

