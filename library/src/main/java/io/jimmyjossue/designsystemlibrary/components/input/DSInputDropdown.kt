package io.jimmyjossue.designsystemlibrary.components.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.toSize
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
    trailingIcon: DSInputIcon? = null,
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
            modifier = modifier
                .clip(shape = shapeInput)
                .clickable(enabled = true, onClick = onShowMenu)
                .onGloballyPositioned(onGloballyPositioned = onGlobalPosition),
            trailingIcon = DSInputIcon(
                icon = when (expandedMenuState.value) {
                    true -> R.drawable.ic_system_notification
                    false -> R.drawable.ic_navigation_back
                }
            ),
            leadingIcon = leadingIcon,
            value = value?.display().orEmpty(),
            config = DSInputConfig(isReadOnly = true, placeholder = placeHolder),
            isEnabled = false,
            colors = colors,
            onChangeValue = { }
        )


        MaterialTheme(
            shapes = MaterialTheme.shapes.copy(medium = shape.medium),
            colorScheme = MaterialTheme.colorScheme.copy(
                surface = color.surface,
                onSurface = color.typography,
            ),
        ) {
            DropdownMenu(
                modifier = Modifier.width(width = inputSizeState.value.getWidthDp()),
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
                                disabledTextColor =colors.typography.alphaMedium,
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

    DSInputDropdown(
        value = selected.value,
        placeHolder = "Elige una opción",
        options = options,
        trailingIcon = DSInputIcon(icon = iconSearch),
        onChangeSelectOption = { selected.value = it }
    )
}