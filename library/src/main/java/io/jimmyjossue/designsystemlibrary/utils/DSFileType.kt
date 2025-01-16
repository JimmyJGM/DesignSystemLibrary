package io.jimmyjossue.designsystemlibrary.utils

sealed class DSFileType {
    data object Image: DSFileType()
    data object Sound: DSFileType()
    data object Video: DSFileType()
    data object Doc: DSFileType()
    data object Spreadsheet: DSFileType()
    data object Code: DSFileType()
    data object Compressed: DSFileType()
    data object All: DSFileType()
    data class Custom(val types: List<String>): DSFileType()
}
