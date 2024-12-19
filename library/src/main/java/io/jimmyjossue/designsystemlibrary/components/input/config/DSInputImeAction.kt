package io.jimmyjossue.designsystemlibrary.components.input.config

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.text.input.ImeAction

sealed class DSInputImeAction(open val action: () -> Unit) {
    data class Done(override val action: () -> Unit = {}) : DSInputImeAction(action = action)
    data class Search(override val action: () -> Unit = {}) : DSInputImeAction(action = action)
    data class Nex(override val action: () -> Unit = {}) : DSInputImeAction(action = action)
}

internal fun DSInputImeAction.toInputImeAction() = when (this) {
    is DSInputImeAction.Done -> ImeAction.Done
    is DSInputImeAction.Nex -> ImeAction.Next
    is DSInputImeAction.Search -> ImeAction.Search
}

internal fun DSInputImeAction.toKeyboardAction() = when (this) {
    is DSInputImeAction.Done -> KeyboardActions(onDone = { this@toKeyboardAction.action() })
    is DSInputImeAction.Nex -> KeyboardActions(onNext = { this@toKeyboardAction.action() })
    is DSInputImeAction.Search -> KeyboardActions(onSearch = { this@toKeyboardAction.action() })
}