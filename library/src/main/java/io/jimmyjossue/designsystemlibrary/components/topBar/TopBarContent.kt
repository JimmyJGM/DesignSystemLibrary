package io.jimmyjossue.designsystemlibrary.components.topBar

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.image.DSImage
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.textdecorator.decoratedAnnotatedString

enum class DSImageShape {
    CIRCLE, RECTANGLE, ROUND_CORNER
}

data class DSTopBarContent (
    val title: String? = null,
    val subtitle: String? = null,
    val image: DSTopBarImage? = null,
)

sealed class DSTopBarImage(
    open val shape: DSImageShape = DSImageShape.RECTANGLE,
    open val onClick: (() -> Unit)? = null,
) {
    data class Url(
        val path: String,
        override val shape: DSImageShape = DSImageShape.RECTANGLE,
        override val onClick: (() -> Unit)? = null,
    ): DSTopBarImage(shape = shape, onClick = onClick)

    data class Resource(
        @DrawableRes val id: Int,
        override val shape: DSImageShape = DSImageShape.RECTANGLE,
        override val onClick: (() -> Unit)? = null,
    ): DSTopBarImage(shape = shape, onClick = onClick)
}

@Composable
internal fun RowScope.TopBarContent(
    model: DSTopBarContent,
    contentColor: Color,
) {
    Row(
        modifier = Modifier
            .heightIn(min = dimension.semiLarge)
            .weight(1f),
        horizontalArrangement = Arrangement.spacedBy(dimension.small),
        //verticalAlignment = Alignment.CenterVertically,
    ) {
        when (model.image) {
            is DSTopBarImage.Resource -> model.image.ImageResource()
            is DSTopBarImage.Url -> model.image.ImageURL()
            null -> Unit
        }

        if (!model.title.isNullOrBlank()) {
            Text(
                modifier = Modifier.heightIn(min = dimension.semiLarge),
                text = model.title.decoratedAnnotatedString(),
                style = typography.title,
                color = contentColor,
            )
        }
    }
}

@Composable
private fun DSTopBarImage.Resource.ImageResource() {
    Icon(
        modifier = getImageModifier(shape = shape.getShape(), onClick = onClick),
        painter = painterResource(id = id),
        tint = Color.Unspecified,
        contentDescription = null,
    )
}

@Composable
private fun DSTopBarImage.Url.ImageURL() {
    DSImage(
        modifier = getImageModifier(shape = shape.getShape(), onClick = onClick),
        load = R.drawable.ic_state_success,
        error = R.drawable.ic_state_error,
        url = path,
    )
}

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
private fun getImageModifier(
    shape: Shape,
    onClick: (() -> Unit)?
) = Modifier
    .widthIn(min = dimension.semiLarge.minus(dimension.smalled), max = dimension.extraLarge.times(other = 5))
    .heightIn(min = dimension.semiLarge.minus(dimension.smalled), max = 32.dp)
    .clip(shape = shape)
    .clickable(
        interactionSource = null,
        indication = null,
        enabled = onClick != null,
        onClick = { onClick?.invoke() }
    )
    .padding(dimension.smalled)

@Composable
private fun DSImageShape.getShape() = when (this) {
    DSImageShape.ROUND_CORNER -> RoundedCornerShape(size = dimension.smalled.times(other = 3))
    DSImageShape.RECTANGLE -> RoundedCornerShape(size = dimension.smalled)
    DSImageShape.CIRCLE -> CircleShape
}
