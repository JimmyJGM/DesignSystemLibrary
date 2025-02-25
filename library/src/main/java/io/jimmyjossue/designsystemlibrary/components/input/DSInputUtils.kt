package io.jimmyjossue.designsystemlibrary.components.input

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaSemiLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography

object DSInputUtils {

    private val iconSize @Composable get() = dimension.semiLarge
    private val iconPadding @Composable get() = dimension.smalled

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
        unfocusedIndicatorColor = typography.alphaLow,
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
    fun getInputColors(
        background: Color = color.background,
        typography: Color = color.typography,
        primary: Color = color.primary,
        accent: Color = color.accent,
        error: Color = color.error,
    ) = DSInputColors(
        background = background,
        typography = typography,
        primary = primary,
        accent = accent,
        error = error,
    )

    private fun text(
        modifier: Modifier = Modifier,
        text: String? = null,
        textStyle: TextStyle,
    ): (@Composable () -> Unit)? = text?.let {
        {
            Text(
                modifier = modifier,
                text = text,
                style = textStyle,
            )
        }
    }

    @Composable
    internal fun placeholder(
        text: String? = null,
    ) = text(
        text = text,
        textStyle = typography.body,
    )

    @Composable
    internal fun label(
        modifier: Modifier = Modifier,
        text: String? = null,
    ) = text(
        modifier = modifier,
        text = text,
        textStyle = typography.caption,
    )

    @Composable
    internal fun icon(
        @DrawableRes icon: Int? = null,
        isEnabled: Boolean = true,
        onClick: (() -> Unit)? = null,
    ): @Composable (() -> Unit)? = icon?.let {
        @Composable {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(size = iconSize)
                    .clickable(
                        enabled = isEnabled && onClick != null,
                        onClick = { onClick?.invoke() }
                    )
                    .padding(all = iconPadding),
            )
        }
    }
}
