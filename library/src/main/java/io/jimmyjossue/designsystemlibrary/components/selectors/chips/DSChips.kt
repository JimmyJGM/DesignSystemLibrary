package io.jimmyjossue.designsystemlibrary.components.selectors.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.components.selectors.chips.config.DSChipColors
import io.jimmyjossue.designsystemlibrary.components.selectors.chips.config.DSChipsConfig
import io.jimmyjossue.designsystemlibrary.components.selectors.chips.config.DSChipsUtils
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLower
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.fx.DSShakingState
import io.jimmyjossue.designsystemlibrary.utils.fx.rememberShakingState
import io.jimmyjossue.designsystemlibrary.utils.fx.shakable
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DSChips(
    modifier: Modifier = Modifier,
    title: String? = null,
    helper: String? = null,
    chips: List<DSChip>,
    contentShape: Shape = shape.small,
    colors: DSChipColors = DSChipsUtils.getColors(),
    config: DSChipsConfig = DSChipsUtils.getConfig(),
    onChangeSelectChip: (String, Boolean) -> Unit,
) {
    val shakeState = rememberShakingState()
    val scope = rememberCoroutineScope()

    fun onShake(key: String) {
        scope.launch { shakeState.shake(key = key, animationDuration = 20) }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = colors.background, shape = contentShape)
            .padding(horizontal = config.contentPadding)
            .padding(
                top = config.contentPadding,
                bottom = when (config.contentPadding > config.chipSeparation) {
                    true -> config.contentPadding - config.chipSeparation
                    false -> config.contentPadding
                }
            ),
        verticalArrangement = Arrangement.spacedBy(

            when (config.contentPadding > config.chipSeparation.div(other = 2)) {
                true -> config.contentSeparation.minus(config.chipSeparation.div(other = 2))
                false -> config.contentPadding
            }
        )
    ) {
        if (!title.isNullOrBlank()) {
            Text(
                text = title,
                style = typography.title,
                color = colors.typography,
            )
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(space = config.chipSeparation),
            horizontalArrangement = Arrangement.spacedBy(space = config.chipSeparation),
        ) {
            chips.forEach { chip ->
                shakeState.addShakeble(key = chip.id)
                ChipSelectable(
                    colors = colors,
                    text = chip.text,
                    isSelected = chip.isSelected,
                    isEnabled = chip.isEnabled,
                    shape = RoundedCornerShape(config.chipsRadiusShape),
                    onSelected = {
                        val selected = chips.count { it.isSelected }

                        when {
                            !chip.isSelected && selected >= config.maxSelected -> onShake(chip.id)
                            else -> onChangeSelectChip(chip.id, !chip.isSelected)
                        }
                    },
                    modifier = when (!chip.isSelected && chip.isEnabled) {
                        true -> Modifier.shakable(key = chip.id, state = shakeState)
                        false -> Modifier
                    },
                )
            }
        }
        if (!helper.isNullOrBlank()) {
            Text(
                text = helper,
                style = typography.caption,
                color = colors.typography.alphaMedium,
                modifier = Modifier.padding(bottom = config.chipSeparation),
            )
        }
    }
}



@Composable
internal fun ChipSelectable(
    modifier: Modifier,
    text: String,
    isSelected: Boolean,
    isEnabled: Boolean,
    shape: Shape,
    colors: DSChipColors,
    onSelected: () -> Unit,
) {
    Row(
        modifier = modifier
            .widthIn(72.dp, Dp.Infinity)
            .alpha(alpha = if (isEnabled) 1f else 0.35f)
            .clip(shape = shape)
            .background(
                shape = shape,
                color = when {
                    !isEnabled -> colors.disabled
                    isSelected -> colors.primary
                    else -> colors.surface
                }
            )
            .clickable(
                enabled = isEnabled,
                onClick = onSelected,
                role = Role.RadioButton
            )
            .border(
                width = 1.dp,
                shape = shape,
                color = when {
                    isSelected -> Color.Transparent
                    else -> colors.typography.copy(alpha = 0.01f)
                }
            )
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp,
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            style = typography.caption,
            color = when {
                !isEnabled -> colors.typography.copy(alpha = 0.75f)
                isSelected -> colors.onPrimary
                else -> colors.onSurface
            },
        )
    }
}

internal val staticChips = listOf(
    DSChip(id = "1234567", text = "Bebida"),
    DSChip(id = "4567", text = "Postre"),
    DSChip(id = "91836728", text = "Seleccionado"),
    DSChip(id = "91827", text = "Septimo"),
    DSChip(id = "5632781", text = "SextoSentido", isEnabled = false),
    DSChip(id = "0976123", text = "Novenisimo"),
    DSChip(id = "67832", text = "DecimoPrimero", isEnabled = false),
)

@Preview(showBackground = true, backgroundColor = 0xfff1f1f1)
@Composable
internal fun ChipsPreview() {
    val chips = remember { mutableStateOf(staticChips) }

    fun changeChips(id: String, isSelected: Boolean) {
        chips.value = chips.value.map {
            when (it.id == id && it.isSelected != isSelected) {
                true -> it.copy(isSelected = isSelected)
                false -> it
            }
        }
    }

    Box(
        modifier = Modifier.padding(dimension.medium)
    ) {
        DSChips(
            helper = "Helper text gives context about a field's input, such as how the input will be used. It should be visible either persistently or only on focus.",
            chips = chips.value,
            config = DSChipsUtils.getConfig(
                maxSelected = 3,
                contentPadding = dimension.medium
            ),
            colors = DSChipsUtils.getColors(),
            onChangeSelectChip = ::changeChips
        )
    }
}