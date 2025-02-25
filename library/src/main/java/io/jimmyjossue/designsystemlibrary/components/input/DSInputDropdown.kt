package io.jimmyjossue.designsystemlibrary.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.input.config.DSInputConfig
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.iconSearch

interface DSInputDropdown<T> {
    fun display(): String
    fun  value(): T
}

@Composable
private fun Size.getWidthDp(): Dp = with(LocalDensity.current) {
    this@getWidthDp.width.toDp()
}

@Composable
internal fun <T> DSInputDropdown(
    modifier: Modifier = Modifier,
    placeHolder: String? = null,
    value: DSInputDropdown<T>? = null,
    shapeInput: Shape = shape.small,
    leadingIcon: DSInputIcon? = null,
    isEnabled: Boolean = true,
    options: List<DSInputDropdown<T>> = emptyList(),
    colors: DSInputColors = DSInputUtils.getInputColors(),
    onChangeSelectOption: (T) -> Unit,
) {
    val inputSizeState = remember { mutableStateOf(Size.Zero) }
    val expandedMenuState = remember { mutableStateOf(false) }
    val onShowMenu = { expandedMenuState.value = true }
    val onHideMenu = { expandedMenuState.value = false }
    val onGlobalPosition: (LayoutCoordinates) -> Unit = { inputSizeState.value = it.size.toSize() }
    val onSelectedOption = { selected: DSInputDropdown<T> ->
        onChangeSelectOption(selected.value())
        onHideMenu()
    }

    Box {

        DSInputText(
            modifier = modifier.onGloballyPositioned(onGloballyPositioned = onGlobalPosition),
            trailingIcon = DSInputIcon(
                onClick = onShowMenu,
                icon = when (expandedMenuState.value) {
                    true -> R.drawable.ds_ic_action_up_light
                    false -> R.drawable.ds_ic_action_down_light
                }
            ),
            leadingIcon = leadingIcon,
            value = value?.display().orEmpty(),
            config = DSInputConfig(
                isReadOnly = true,
                placeholder = placeHolder,
                maxLines = 1,
            ),
            shapeInput = shapeInput,
            isEnabled = isEnabled,
            colors = colors,
            onChangeValue = { }
        )

        Box(
            modifier = Modifier
                .clip(shape = shapeInput)
                .matchParentSize()
                .clickable(enabled = isEnabled, onClick = onShowMenu)
        )
        DropdownMenu(
            modifier = Modifier
                .width(width = inputSizeState.value.getWidthDp())
                .background(color = color.surface)
                .clip(shape = shape.small),
            expanded = expandedMenuState.value,
            onDismissRequest = onHideMenu,
        ) {
            Column {
                options.forEach { option ->
                    DropdownMenuItem(
                        colors = MenuItemColors(
                            textColor = colors.typography,
                            leadingIconColor = colors.typography,
                            trailingIconColor = colors.typography,
                            disabledTextColor = colors.typography.alphaMedium,
                            disabledLeadingIconColor = colors.typography.alphaMedium,
                            disabledTrailingIconColor = colors.typography.alphaMedium,
                        ),
                        onClick = { onSelectedOption(option) },
                        text = {
                            Text(
                                text = option.display(),
                                style = typography.body,
                                color = color.typography
                            )
                        },
                    )
                }
            }
        }
    }
}

data class User(
    val name: String,
    val lastName: String,
): DSInputDropdown<User> {
    override fun display() = "$name $lastName"
    override fun value() = this
}

@Preview
@Composable
fun PreviewDSInputDropdown() {
    val selected = remember { mutableStateOf<User?>(null) }
    val options = listOf(
        User(name = "Luz Belen", lastName = "Salazar Mejia"),
        User(name = "jimmy Jossue", lastName = "Gijon Medel"),
        User(name = "Josmar Julian", lastName = "Gijon Medel"),
        User(name = "Joseph Joel", lastName = "Gijon Medel"),
        User(name = "Israel", lastName = "Salazar Mejia"),
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DSInputDropdown(
            value = selected.value,
            placeHolder = "Elige una opción",
            options = options,
            onChangeSelectOption = { selected.value = it }
        )
    }
}