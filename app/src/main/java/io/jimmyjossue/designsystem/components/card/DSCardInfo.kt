package io.jimmyjossue.designsystem.components.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystem.R
import io.jimmyjossue.designsystem.components.separator.DSSpacer
import io.jimmyjossue.designsystem.theme.alphaLow
import io.jimmyjossue.designsystem.theme.alphaMedium
import io.jimmyjossue.designsystem.theme.color
import io.jimmyjossue.designsystem.theme.dimension
import io.jimmyjossue.designsystem.theme.shape
import io.jimmyjossue.designsystem.theme.typography
import io.jimmyjossue.designsystem.utils.parseDecoratedAnnotatedString
import io.jimmyjossue.designsystem.utils.withStyle

enum class DSCardInfoType {
    SUCCESS,
    INFORMATION,
    WARNING,
    ERROR
}

enum class DSSizeType {
    SMALL,
    NORMAL,
    LARGE,
    EXTRA_LARGE
}

@Composable
fun DSCardInfo(
    message: String,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null,
    cardType: DSCardInfoType = DSCardInfoType.INFORMATION,
    sizeType: DSSizeType = DSSizeType.NORMAL,
    shapeCard: Shape = shape.small,
    onClick: (() -> Unit)? = null,
) {
    val lineCount = remember { mutableIntStateOf(1) }

    val clickableModifier = onClick?.let {
        Modifier.clickable(
            onClick = onClick,
            role = Role.Button,
            interactionSource = remember { MutableInteractionSource() },
            indication = LocalIndication.current
        )
    } ?: Modifier

    if (message.isNotBlank()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = shapeCard)
                .then(other = clickableModifier)
                .background(color = cardType.getBackgroundColor(), shape = shapeCard)
                .border(width = 1.dp, color = cardType.getBorderColor().alphaLow, shape = shapeCard)
                .padding(all = dimension.medium),
            horizontalArrangement = Arrangement.spacedBy(dimension.medium),
            verticalAlignment = if (lineCount.intValue == 1) Alignment.CenterVertically else Alignment.Top
        ) {
            if (icon != null) {
                Icon(
                    modifier = Modifier.size(size = sizeType.getIconSize()),
                    painter = painterResource(id = icon),
                    tint = cardType.getBorderColor(),
                    contentDescription = null,
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = message.parseDecoratedAnnotatedString(),
                style = sizeType.getTypography(),
                color = cardType.getContentColor(),
                onTextLayout = {
                    if (lineCount.intValue != it.lineCount) {
                        lineCount.intValue != it.lineCount
                    }
                }
            )
        }
    }
}

@Composable
internal fun DSCardInfoType.getBackgroundColor() = when (this) {
    DSCardInfoType.SUCCESS -> color.successSurface
    DSCardInfoType.INFORMATION -> color.surface
    DSCardInfoType.WARNING -> color.warningSurface
    DSCardInfoType.ERROR -> color.errorSurface
}

@Composable
internal fun DSCardInfoType.getBorderColor() = when (this) {
    DSCardInfoType.SUCCESS -> color.success
    DSCardInfoType.INFORMATION -> color.typography.alphaMedium
    DSCardInfoType.WARNING -> color.warning
    DSCardInfoType.ERROR -> color.error
}

@Composable
internal fun DSCardInfoType.getContentColor() = when (this) {
    DSCardInfoType.SUCCESS -> color.successContent
    DSCardInfoType.INFORMATION -> color.typography.alphaMedium
    DSCardInfoType.WARNING -> color.warningContent
    DSCardInfoType.ERROR -> color.errorContent
}

@Composable
internal fun DSSizeType.getTypography() = when (this) {
    DSSizeType.SMALL -> typography.caption
    DSSizeType.NORMAL -> typography.body
    DSSizeType.LARGE -> typography.title
    DSSizeType.EXTRA_LARGE -> typography.title
}

@Composable
internal fun DSSizeType.getIconSize() = when (this) {
    DSSizeType.SMALL -> dimension.medium + dimension.smalled.times(2)
    DSSizeType.NORMAL -> dimension.semiLarge
    DSSizeType.LARGE -> dimension.semiLarge + dimension.smalled.times(2)
    DSSizeType.EXTRA_LARGE -> dimension.large - dimension.small
}

@Preview(showBackground = true, backgroundColor = 0xfff)
@Composable
private fun Preview() {
    val state = remember { mutableStateOf(DSCardInfoType.INFORMATION) }
    val message = "Mensaje que se mostrara en la <sb>card."

    fun DSCardInfoType.getIcon(): Int {
        return when (this) {
            DSCardInfoType.SUCCESS -> R.drawable.ic_state_success
            DSCardInfoType.INFORMATION -> R.drawable.ic_state_information
            DSCardInfoType.WARNING -> R.drawable.ic_state_warning
            DSCardInfoType.ERROR -> R.drawable.ic_state_error
        }
    }

    fun changeState() {
        state.value = when (state.value) {
            DSCardInfoType.SUCCESS -> DSCardInfoType.ERROR
            DSCardInfoType.INFORMATION -> DSCardInfoType.SUCCESS
            DSCardInfoType.WARNING -> DSCardInfoType.INFORMATION
            DSCardInfoType.ERROR -> DSCardInfoType.WARNING
        }
    }

    Column(
        modifier = Modifier.padding(all = dimension.small),
        verticalArrangement = Arrangement.spacedBy(space = dimension.small)
    ) {
        Text(text = "Click en las cards para cambiar diseño")
        DSSpacer(size = dimension.smalled)

        DSCardInfo(
            message = message,
            icon = state.value.getIcon(),
            cardType = state.value,
            sizeType = DSSizeType.SMALL,
            onClick = ::changeState
        )
        DSCardInfo(
            message = message,
            icon = state.value.getIcon(),
            cardType = state.value,
            sizeType = DSSizeType.NORMAL,
            onClick = ::changeState
        )
        DSCardInfo(
            message = message,
            icon = state.value.getIcon(),
            cardType = state.value,
            sizeType = DSSizeType.LARGE,
            onClick = ::changeState
        )
        DSCardInfo(
            message = message,
            icon = state.value.getIcon(),
            cardType = state.value,
            sizeType = DSSizeType.EXTRA_LARGE,
            onClick = ::changeState
        )
    }
}
