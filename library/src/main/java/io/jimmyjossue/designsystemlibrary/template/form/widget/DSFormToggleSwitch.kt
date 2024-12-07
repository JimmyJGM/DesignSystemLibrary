package io.jimmyjossue.designsystemlibrary.template.form.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.jimmyjossue.designsystemlibrary.components.toggle.DSToggleSwitch
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
    Column(
        verticalArrangement = Arrangement.spacedBy(dimension.small),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimension.small)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = dimension.semiLarge)
            ) {
                DSFormLabel(
                    value = model.label.orEmpty(),
                    style = typography.body,
                    colors = colors,
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
