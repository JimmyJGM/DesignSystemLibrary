package io.jimmyjossue.designsystemlibrary.template.form.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.components.toggle.DSToggleSwitch
import io.jimmyjossue.designsystemlibrary.template.form.DSFormUtils
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography

@Composable
internal fun DSFormToggleSwitch(
    colors: DSFormColors,
    model: DSFormElement.ToggleSwitch,
    onChangeValue: (String, Any) -> Unit,
) {
    val lineCount = remember { mutableIntStateOf(1) }
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = if (lineCount.intValue > 1) dimension.smalled else dimension.none
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimension.small)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = dimension.semiLarge),
                contentAlignment = Alignment.CenterStart

            ) {
                DSFormLabel(
                    value = model.label.orEmpty(),
                    style = typography.body,
                    colors = colors,
                    onTextLayout = { lineCount.intValue = it.lineCount }
                )
            }
            DSToggleSwitch(
                isSelected = model.isSelected,
                isEnabled = model.isEnabled,
                colorPrimary = colors.primary,
                colorPrimaryDisabled = colors.primaryDisabled,
                colorSurface = colors.surfaceDark,
                colorOnPrimary = colors.onPrimary,
                onClick = {
                    onChangeValue(
                        model.key,
                        model.isSelected.not()
                    )
                }
            )
        }
        LabelHelper(
            value = model.helper.orEmpty(),
            colors = colors,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDSFormToggleSwitch() {
    DSFormToggleSwitch(
        colors = DSFormUtils.getColors(),
        onChangeValue = { _, _ ->

        },
        model = DSFormElement.ToggleSwitch(
            key = "",
            displayName = "",
            label = "Selecciona esta opccion Selecciona esta opccion para acceder a nustras opccion para acceder a nustras funciones premiun",
            helper = "este dato es requeriso este dato es requeriso este dato es requeriso este dato es requeriso este dato es requeriso este dato es requeriso"
        ),
    )
}