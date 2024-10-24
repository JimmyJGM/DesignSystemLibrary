package io.jimmyjossue.designsystem.components.input

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import io.jimmyjossue.designsystem.theme.alphaHigh
import io.jimmyjossue.designsystem.theme.alphaLow
import io.jimmyjossue.designsystem.theme.alphaMedium
import io.jimmyjossue.designsystem.theme.alphaSemiLow
import io.jimmyjossue.designsystem.theme.color
import io.jimmyjossue.designsystem.theme.typography

object DSInputUtils {

    internal fun DSInputColors.toInputColors() = TextFieldColors(
        focusedTextColor = typography,
        unfocusedTextColor = typography,
        disabledTextColor = typography.alphaSemiLow,
        errorTextColor = error,

        focusedContainerColor = background,
        unfocusedContainerColor = background,
        disabledContainerColor = background,
        errorContainerColor = background,

        cursorColor = accent,
        errorCursorColor = error,
        textSelectionColors = TextSelectionColors(handleColor = typography.alphaLow, backgroundColor = accent),

        focusedIndicatorColor = primary,
        unfocusedIndicatorColor = typography.alphaHigh,
        disabledIndicatorColor = typography.alphaSemiLow,
        errorIndicatorColor = error,

        focusedLeadingIconColor = typography.alphaHigh,
        unfocusedLeadingIconColor = typography.alphaSemiLow,
        disabledLeadingIconColor = typography.alphaLow,
        errorLeadingIconColor = error,

        focusedTrailingIconColor = typography.alphaHigh,
        unfocusedTrailingIconColor = typography.alphaSemiLow,
        disabledTrailingIconColor = typography.alphaLow,
        errorTrailingIconColor = error,

        focusedLabelColor = typography,
        unfocusedLabelColor = typography.alphaHigh,
        disabledLabelColor = typography.alphaSemiLow,
        errorLabelColor = error,

        focusedPlaceholderColor = typography.alphaSemiLow,
        unfocusedPlaceholderColor = typography.alphaSemiLow,
        disabledPlaceholderColor = typography.alphaLow,
        errorPlaceholderColor = error.alphaMedium,

        focusedSupportingTextColor = typography.alphaMedium,
        unfocusedSupportingTextColor = typography.alphaMedium,
        disabledSupportingTextColor = typography.alphaSemiLow,
        errorSupportingTextColor = error.alphaMedium,

        focusedPrefixColor = typography,
        unfocusedPrefixColor = typography,
        disabledPrefixColor = typography,
        errorPrefixColor = error,

        focusedSuffixColor = typography,
        unfocusedSuffixColor = typography,
        disabledSuffixColor = typography,
        errorSuffixColor = error,
    )

    @Composable
    fun getInputTextColors() = DSInputColors(
        background = color.background,
        typography = color.typography,
        primary = color.primary,
        accent = color.accent,
        error = color.error,
    )

    private fun text(
        modifier: Modifier = Modifier,
        text: String? = null,
        textStyle: TextStyle,
        color: Color,
    ): (@Composable () -> Unit)? = text?.let {
        {
            Text(
                modifier = modifier,
                text = text,
                style = textStyle,
                color = color,
            )
        }
    }

    @Composable
    internal fun placeholder(
        text: String? = null,
    ) = text(
        text = text,
        textStyle = typography.body,
        color = color.typography.alphaMedium,
    )

    @Composable
    internal fun label(
        modifier: Modifier = Modifier,
        text: String? = null,
    ) = text(
        modifier = modifier,
        text = text,
        textStyle = typography.caption,
        color = color.typography.alphaHigh,
    )

}
