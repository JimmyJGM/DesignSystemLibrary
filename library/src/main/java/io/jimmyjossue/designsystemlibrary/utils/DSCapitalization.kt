package io.jimmyjossue.designsystemlibrary.utils

import androidx.compose.ui.text.input.KeyboardCapitalization

enum class DSCapitalization {
    None, Characters, Words, Sentences
}

internal fun DSCapitalization.toKeyboardCapitalization() = when (this) {
    DSCapitalization.None -> KeyboardCapitalization.None
    DSCapitalization.Characters -> KeyboardCapitalization.Characters
    DSCapitalization.Words -> KeyboardCapitalization.Words
    DSCapitalization.Sentences -> KeyboardCapitalization.Sentences
}
