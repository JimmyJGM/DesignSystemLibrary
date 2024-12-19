package io.jimmyjossue.designsystemlibrary.components.input.config

import androidx.compose.ui.text.input.KeyboardType

enum class DSKeyboardType {
    Text,
    Number,
    Phone,
    Uri,
    Email,
    Password,
    NumberPassword,
    Decimal,
}

internal fun DSKeyboardType.toInputKeyboardType() = when (this) {
    DSKeyboardType.Text -> KeyboardType.Text
    DSKeyboardType.Number -> KeyboardType.Number
    DSKeyboardType.Phone -> KeyboardType.Phone
    DSKeyboardType.Uri -> KeyboardType.Uri
    DSKeyboardType.Email -> KeyboardType.Email
    DSKeyboardType.Password -> KeyboardType.Password
    DSKeyboardType.NumberPassword -> KeyboardType.NumberPassword
    DSKeyboardType.Decimal -> KeyboardType.Decimal
}