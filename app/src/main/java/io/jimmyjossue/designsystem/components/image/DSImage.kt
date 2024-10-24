package io.jimmyjossue.designsystem.components.image

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

private const val contentDescription = "image"

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DSImage(
    url: String,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    @DrawableRes error: Int? = null,
    @DrawableRes load: Int? = null
) {
    if (url.isNotBlank()) {
        GlideImage(
            model = url,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
            contentDescription = contentDescription,
            failure = error?.let { placeholder(it) },
            loading = load?.let { placeholder(it) },
        )
    }
}