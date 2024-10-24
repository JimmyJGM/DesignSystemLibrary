package io.jimmyjossue.designsystem.components.topBar

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystem.R
import io.jimmyjossue.designsystem.components.image.DSImage
import io.jimmyjossue.designsystem.theme.dimension
import io.jimmyjossue.designsystem.theme.typography
import io.jimmyjossue.designsystem.utils.imageId
import io.jimmyjossue.designsystem.utils.imageUrl
import io.jimmyjossue.designsystem.utils.onClick
import io.jimmyjossue.designsystem.utils.parseDecoratedAnnotatedString

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
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.spacedBy(dimension.small),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (model.image) {
            is DSTopBarImage.Resource -> model.image.ImageResource()
            is DSTopBarImage.Url -> model.image.ImageURL()
            null -> Unit
        }

        if (!model.title.isNullOrBlank()) {
            Text(
                text = model.title.parseDecoratedAnnotatedString(),
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

@Preview(widthDp = 375, showBackground = true)
@Composable
private fun PreviewDSTopBarContent() {
    val model = DSTopBarContent(
        title = "Titulo de la barra de superior",
        image = DSTopBarImage.Resource(id = R.drawable.ic_launcher_foreground, onClick = { })
    )
    val models = listOf(
        model,
        model.copy(image = DSTopBarImage.Resource(imageId, DSImageShape.CIRCLE, onClick)),
        model.copy(image = DSTopBarImage.Resource(imageId, DSImageShape.ROUND_CORNER, onClick)),
        model.copy(image = DSTopBarImage.Url(imageUrl, DSImageShape.RECTANGLE, onClick)),
        model.copy(image = DSTopBarImage.Url(imageUrl, DSImageShape.CIRCLE, onClick)),
        model.copy(image = DSTopBarImage.Url(imageUrl, DSImageShape.ROUND_CORNER, onClick)),
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(dimension.smalled)
    ) {
        models.forEach {
            Row {
                TopBarContent(model = it, contentColor = Color.Black)
            }
        }
    }
}