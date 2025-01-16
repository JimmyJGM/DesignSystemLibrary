package io.jimmyjossue.designsystemlibrary.components.dialog.model

interface DSDialogOption {
    val text: String
    val caption: String?
    val id: String
}

enum class DSDialogOptionType {
    Check, Radio
}