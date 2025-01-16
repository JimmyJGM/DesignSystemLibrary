package io.jimmyjossue.designsystemlibrary.utils

import androidx.compose.ui.text.input.KeyboardCapitalization

enum class DSCapitalizationType {
    None, Characters, Words, Sentences
}

internal fun DSCapitalizationType.toKeyboardCapitalization() = when (this) {
    DSCapitalizationType.None -> KeyboardCapitalization.None
    DSCapitalizationType.Characters -> KeyboardCapitalization.Characters
    DSCapitalizationType.Words -> KeyboardCapitalization.Words
    DSCapitalizationType.Sentences -> KeyboardCapitalization.Sentences
}
