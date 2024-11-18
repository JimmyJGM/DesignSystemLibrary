package io.jimmyjossue.designsystemlibrary.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight

internal fun String.withStyle(
    character: String? = null,
    fontWeight: FontWeight? = null,
    color: Color? = null,
): AnnotatedString {
    return if (character != null && !this@withStyle.contains(character)) {
        return buildAnnotatedString { append(this@withStyle) }
    } else {
        buildAnnotatedString {
            val indexStart = this@withStyle.indexOf(character.orEmpty())
            val endIndex = (indexStart + (character?.count() ?: 0))

            append(this@withStyle)

            addStyle(
                style = SpanStyle(color = color ?: Color. Unspecified, fontWeight = fontWeight),
                start = indexStart,
                end = endIndex,
            )
        }
    }
}
