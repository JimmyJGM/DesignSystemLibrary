package io.jimmyjossue.designsystem.components.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import io.jimmyjossue.designsystem.components.input.DSInputUtils.label
import io.jimmyjossue.designsystem.components.input.DSInputUtils.placeholder
import io.jimmyjossue.designsystem.components.input.DSInputUtils.toInputColors
import io.jimmyjossue.designsystem.components.separator.DSSpacer
import io.jimmyjossue.designsystem.theme.dimension
import io.jimmyjossue.designsystem.theme.shape
import io.jimmyjossue.designsystem.theme.typography

data class DSInputColors(
    val background: Color,
    val typography: Color,
    val primary: Color,
    val accent: Color,
    val error: Color,
)

data class DSInputConfig(
    val placeholder: String? = null,
    val label: String? = null,
    val helper: String? = null,
    val isReadOnly: Boolean = false,
    val isSingleLine: Boolean = false,
    val maxLines: Int = Int.MAX_VALUE
)

@Composable
fun DSInputText(
    value: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    shapeInput: Shape = shape.small,
    config: DSInputConfig = DSInputConfig(),
    colors: DSInputColors = DSInputUtils.getInputTextColors(),
    onChangeValue: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        modifier = modifier,
        enabled = isEnabled,
        shape = shapeInput,
        readOnly = config.isReadOnly,
        textStyle = typography.body,

        label = label(text = config.label),
        placeholder = placeholder(text = config.placeholder),
        leadingIcon = null,
        trailingIcon = null,
        supportingText = label(
            text = config.helper,
            modifier = Modifier.padding(top = dimension.smalled)
        ),
        isError = isError,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions.Default,

        singleLine = config.isSingleLine,
        maxLines = if (config.isSingleLine) 1 else config.maxLines,
        minLines = 1,
        colors = colors.toInputColors(),

        onValueChange = onChangeValue,
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val text = remember { mutableStateOf(value = "") }

    Column(
        modifier = Modifier.padding(dimension.medium)
    ) {
        DSInputText(
            value = text.value,
            config = DSInputConfig(
                placeholder = "Escribe tu nombre",
                label = "Nombre",
                helper = "No olvides escribir tu nombre",
            ),
            onChangeValue = { text.value = it }
        )
        DSSpacer(size = dimension.medium)
        DSInputText(
            value = text.value,
            config = DSInputConfig(
                placeholder = "Escribe tu nombre",
                label = "Nombre",
                helper = "No olvides escribir tu nombre",
            ),
            onChangeValue = { text.value = it }
        )
    }
}