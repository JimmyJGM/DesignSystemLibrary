package io.jimmyjossue.designsystemlibrary.components.input

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import io.jimmyjossue.designsystemlibrary.components.input.DSInputUtils.toInputColors
import io.jimmyjossue.designsystemlibrary.components.input.config.DSInputConfig
import io.jimmyjossue.designsystemlibrary.components.input.config.DSInputImeAction
import io.jimmyjossue.designsystemlibrary.components.input.config.toKeyboardAction
import io.jimmyjossue.designsystemlibrary.components.input.config.toKeyboardOptions
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacer
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.iconNotification

data class DSInputColors(
    val background: Color,
    val typography: Color,
    val primary: Color,
    val accent: Color,
    val error: Color,
)

data class DSInputIcon(
    @DrawableRes val icon: Int,
    val onClick: (() -> Unit)? = null,
)

@Composable
fun DSInputText(
    value: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    leadingIcon: DSInputIcon? = null,
    trailingIcon: DSInputIcon? = null,
    shapeInput: Shape = shape.small,
    config: DSInputConfig = DSInputConfig(),
    colors: DSInputColors = DSInputUtils.getInputColors(),
    imeAction: DSInputImeAction = DSInputImeAction.Done(),
    onChangeValue: (String) -> Unit,
) {
    CompositionLocalProvider(
        LocalContentColor provides colors.typography
    ) {
        OutlinedTextField(
            value = value,
            modifier = modifier,
            enabled = isEnabled,
            shape = shapeInput,
            readOnly = config.isReadOnly,
            textStyle = typography.body,
            label = DSInputUtils.label(text = config.label),
            placeholder = DSInputUtils.placeholder(text = config.placeholder),
            leadingIcon = DSInputUtils.icon(
                icon = leadingIcon?.icon,
                onClick = leadingIcon?.onClick,
                isEnabled = isEnabled,
            ),
            trailingIcon = DSInputUtils.icon(
                icon = trailingIcon?.icon,
                onClick = trailingIcon?.onClick,
                isEnabled = isEnabled,
            ),
            supportingText = DSInputUtils.label(
                text = config.helper,
                modifier = Modifier.padding(top = dimension.smalled)
            ),
            isError = isError,
            visualTransformation = VisualTransformation.None,
            keyboardOptions = config.toKeyboardOptions(imeAction = imeAction),
            keyboardActions = imeAction.toKeyboardAction(),
            singleLine = config.isSingleLine,
            maxLines = if (config.isSingleLine) 1 else config.maxLines,
            minLines = 1,
            colors = colors.toInputColors(),
            onValueChange = onChangeValue,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val text = remember { mutableStateOf(value = "") }
    fun onChange(value: String) { text.value = value }

    Column(
        modifier = Modifier.padding(dimension.medium)
    ) {
        DSInputText(
            value = text.value,
            onChangeValue = ::onChange,
            config = DSInputConfig(
                placeholder = "Escribe tu nombre",
                label = "Nombre",
                helper = "No olvides escribir tu nombre",
            ),
        )
        DSSpacer(size = dimension.medium)
        DSInputText(
            value = text.value,
            onChangeValue = ::onChange,
            trailingIcon = DSInputIcon(iconNotification),
            config = DSInputConfig(
                placeholder = "Escribe tu nombre",
                label = "Nombre",
                helper = "No olvides escribir tu nombre",
            ),
        )
    }
}
