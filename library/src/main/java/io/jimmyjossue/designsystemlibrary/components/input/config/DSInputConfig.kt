package io.jimmyjossue.designsystemlibrary.components.input.config

import androidx.compose.foundation.text.KeyboardOptions
import io.jimmyjossue.designsystemlibrary.utils.DSCapitalization
import io.jimmyjossue.designsystemlibrary.utils.toKeyboardCapitalization

data class DSInputConfig(
    val placeholder: String? = null,
    val label: String? = null,
    val helper: String? = null,
    val isReadOnly: Boolean = false,
    val isSingleLine: Boolean = false,
    val maxLines: Int = Int.MAX_VALUE,
    val capitalization: DSCapitalization = DSCapitalization.Words,
    val keyboardType: DSKeyboardType = DSKeyboardType.Text,
    val autoCorrectEnabled: Boolean = false,
    val showKeyboardOnFocus: Boolean = false,
)

internal fun DSInputConfig.toKeyboardOptions(imeAction: DSInputImeAction) = KeyboardOptions(
    capitalization = this.capitalization.toKeyboardCapitalization(),
    keyboardType = this.keyboardType.toInputKeyboardType(),
    showKeyboardOnFocus = this.showKeyboardOnFocus,
    autoCorrectEnabled = this.autoCorrectEnabled,
    imeAction = imeAction.toInputImeAction(),
    platformImeOptions = null,
    hintLocales = null,
)