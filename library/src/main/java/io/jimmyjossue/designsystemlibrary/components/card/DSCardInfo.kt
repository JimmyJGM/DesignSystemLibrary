package io.jimmyjossue.designsystemlibrary.components.card

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonPrimary
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.DSSizeType
import io.jimmyjossue.designsystemlibrary.utils.textdecorator.decoratedAnnotatedString

enum class DSCardInfoType {
    SUCCESS,
    INFORMATION,
    WARNING,
    ERROR
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
            indication = null
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
            verticalAlignment = if (lineCount.intValue > 1) Alignment.Top else Alignment.CenterVertically
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
                text = message.decoratedAnnotatedString(),
                style = sizeType.getTypography(),
                color = cardType.getContentColor(),
                onTextLayout = {
                    if (lineCount.intValue != it.lineCount) {
                        lineCount.intValue = it.lineCount
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
    else -> typography.body
}

@Composable
internal fun DSSizeType.getIconSize() = when (this) {
    DSSizeType.SMALL -> dimension.medium + dimension.smalled.times(2)
    DSSizeType.NORMAL -> dimension.semiLarge
    else -> dimension.large
}

@Preview(showBackground = true)
@Composable
private fun PreviewCardInfo() {
    val sizeState = remember { mutableStateOf(DSSizeType.NORMAL) }
    val message = "<b>¡Oh, vaya!</b>\nParece que ocurrío un error al obtener tu información en pantalla."
    fun changeSize() {
        sizeState.value = when (sizeState.value) {
            DSSizeType.SMALL -> DSSizeType.NORMAL
            DSSizeType.NORMAL -> DSSizeType.LARGE
            DSSizeType.LARGE -> DSSizeType.SMALL
        }
    }

    Column(
        modifier = Modifier.padding(dimension.medium),
        verticalArrangement = Arrangement.spacedBy(dimension.medium)
    ) {
        DSCardInfo(
            message = message,
            modifier = Modifier,
            icon = R.drawable.ic_state_information,
            cardType = DSCardInfoType.INFORMATION,
            sizeType = sizeState.value,
            shapeCard = shape.small,
            onClick = null
        )
        DSCardInfo(
            message = message,
            modifier = Modifier,
            icon = R.drawable.ic_state_success,
            cardType = DSCardInfoType.SUCCESS,
            sizeType = sizeState.value,
            shapeCard = shape.small,
            onClick = null
        )
        DSCardInfo(
            message = message,
            modifier = Modifier,
            icon = R.drawable.ic_state_warning,
            cardType = DSCardInfoType.WARNING,
            sizeType = sizeState.value,
            shapeCard = shape.small,
            onClick = null
        )
        DSCardInfo(
            message = "message",
            modifier = Modifier,
            icon = R.drawable.ic_state_error,
            cardType = DSCardInfoType.ERROR,
            sizeType = sizeState.value,
            shapeCard = shape.small,
            onClick = null
        )

        DSButtonPrimary(
            text = "Cambiar tamaño",
            onClick = ::changeSize
        )
    }
}