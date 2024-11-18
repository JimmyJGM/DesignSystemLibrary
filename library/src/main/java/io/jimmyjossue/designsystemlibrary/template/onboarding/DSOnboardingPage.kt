package io.jimmyjossue.designsystemlibrary.template.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.image.DSImage
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacer
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacerFilled
import io.jimmyjossue.designsystemlibrary.template.onboarding.DSOnboardingUtils.RATIO_IMAGE
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.medium
import io.jimmyjossue.designsystemlibrary.theme.normal
import io.jimmyjossue.designsystemlibrary.theme.typography
import io.jimmyjossue.designsystemlibrary.utils.descriptionOfApp
import io.jimmyjossue.designsystemlibrary.utils.parseDecoratedAnnotatedString
import io.jimmyjossue.designsystemlibrary.utils.titleOfApp

data class DSOnboardingPage(
    val title: String? = null,
    val description: String,
    val image: DSOnboardingImage,
)

sealed class DSOnboardingImage {
    data class Resource(@DrawableRes val id: Int): DSOnboardingImage()
    data class Url(val path: String): DSOnboardingImage()
}

@Composable
internal fun ColumnScope.OnboardingPage(
    title: String?,
    description: String,
    image: DSOnboardingImage?,
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    contentColor: Color,
) {
    Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(dimension.medium),
        horizontalAlignment = alignment
    ) {
        image?.let {
            when (image) {
                is DSOnboardingImage.Resource -> Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(RATIO_IMAGE),
                    painter = painterResource(id = image.id),
                    contentDescription = null
                )

                is DSOnboardingImage.Url -> DSImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(RATIO_IMAGE),
                    url = image.path,
                    error = R.drawable.ic_state_error,
                    load = R.drawable.ic_system_search
                )
            }
        }

        DSSpacer(size = dimension.smalled)

        if (!title.isNullOrBlank()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title.parseDecoratedAnnotatedString(),
                color = contentColor,
                style = typography.titleLarge.medium,
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description.parseDecoratedAnnotatedString(),
            color = contentColor.alphaHigh,
            style = typography.button.normal,
        )

        DSSpacerFilled()
    }
}

@Preview
@Composable
private fun PreviewDSOnboardingPage() {
    Column(
        modifier = Modifier
            .background(color.background)
            .padding(all = dimension.medium)
    ) {
        OnboardingPage(
            title = titleOfApp,
            description = descriptionOfApp,
            image = DSOnboardingImage.Resource(R.drawable.ic_launcher_background),
            contentColor = color.typography
        )
    }
}