package io.jimmyjossue.designsystemlibrary.template.activity.model

import androidx.annotation.FloatRange

sealed class DSBottomModalSheetHeight {
    data object Short : DSBottomModalSheetHeight()
    data object Medium : DSBottomModalSheetHeight()
    data object Full : DSBottomModalSheetHeight()
    data object WrapContent : DSBottomModalSheetHeight()
    data class Percent(
        @FloatRange(from = 0.0, to = 1.0) val fraction: Float
    ) : DSBottomModalSheetHeight()
}

fun DSBottomModalSheetHeight.asShort() = this as? DSBottomModalSheetHeight.Short
fun DSBottomModalSheetHeight.asMedium() = this as? DSBottomModalSheetHeight.Medium
fun DSBottomModalSheetHeight.asFull() = this as? DSBottomModalSheetHeight.Full
fun DSBottomModalSheetHeight.asWrapContent() = this as? DSBottomModalSheetHeight.WrapContent
fun DSBottomModalSheetHeight.asPercent() = this as? DSBottomModalSheetHeight.Percent
