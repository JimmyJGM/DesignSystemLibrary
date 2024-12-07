package io.jimmyjossue.designsystemlibrary.template.form.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.theme.catalog.DSColors
import io.jimmyjossue.designsystemlibrary.theme.catalog.LocalColors
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.DSHorizontal
import io.jimmyjossue.designsystemlibrary.utils.textdecorator.decoratedAnnotatedString

@Composable
internal fun DSFormLabel(
    value: String,
    style: TextStyle,
    align: DSHorizontal = DSHorizontal.LEFT,
    colors: DSFormColors,
) {
    CompositionLocalProvider(
        value = LocalColors provides DSColors(
            typography = colors.typography,
            primary = colors.primary,
            accent = colors.accent,
        )
    ) {
        if (value.isNotBlank()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = value.decoratedAnnotatedString(),
                color = colors.typography,
                style = style,
                textAlign = when (align) {
                    DSHorizontal.LEFT -> TextAlign.Left
                    DSHorizontal.RIGHT -> TextAlign.Start
                }
            )
        }
    }
}

@Composable
internal fun LabelHelper(
    value: String?,
    colors: DSFormColors,
) {
    if (!value.isNullOrBlank()) {
        DSFormLabel(
            value = value,
            style = typography.caption,
            colors = colors.copy(
                typography = colors.typography.alphaMedium
            ),
        )
    }
}
