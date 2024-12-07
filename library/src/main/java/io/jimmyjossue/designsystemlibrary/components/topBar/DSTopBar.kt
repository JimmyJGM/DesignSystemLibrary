package io.jimmyjossue.designsystemlibrary.components.topBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBarUtils.getFraction
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.FALSE
import io.jimmyjossue.designsystemlibrary.utils.TRUE
import io.jimmyjossue.designsystemlibrary.utils.extension.borderBottom
import io.jimmyjossue.designsystemlibrary.utils.onClick
import io.jimmyjossue.designsystemlibrary.utils.textdecorator.decoratedAnnotatedString
import io.jimmyjossue.designsystemlibrary.utils.titleOfApp

data class DSTopBarColors(
    val content: Color,
    val container: Color,
    val accent: Color,
    val containerScrolled: Color,
    val contentScrolled: Color,
    val borderScrolled: Color,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DSTopBar(
    navigation: DSTopBarNavigation? = null,
    content: DSTopBarContent = DSTopBarContent(),
    actions: List<DSTopBarAction> = emptyList(),
    actionsWithContentTint: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    colors: DSTopBarColors = DSTopBarUtils.getColors(),
) {
    val withIcon = false
    val colorContent by DSTopBarUtils.animateContentColor(colors, scrollBehavior.getFraction())
    val colorContainer by DSTopBarUtils.animateContainerColor(colors, scrollBehavior.getFraction())
    val colorBorder by DSTopBarUtils.animateBorderColor(colors, scrollBehavior.getFraction())
    val heightOffsetLimit = with(LocalDensity.current) { -200.0.dp.toPx() }

    @Composable
    fun getPadding(withIcon: Boolean) = when (withIcon) {
        true -> dimension.medium
        false -> dimension.semiLarge.minus(dimension.small)
    }

    SideEffect {
        if (scrollBehavior.state.heightOffsetLimit != heightOffsetLimit) {
            scrollBehavior.state.heightOffsetLimit = heightOffsetLimit
        }
    }

    Surface(
        contentColor = colorContent,
        color = colorContainer,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = DSTopBarUtils.getStatusBarMinHeight())
                .borderBottom(color = colorBorder.alphaMedium)
                .statusBarsPadding()
                .padding(
                    start = getPadding(withIcon = withIcon),
                    top = dimension.small,
                    end = getPadding(withIcon = withIcon),
                    bottom = dimension.small,
                ),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimension.medium),
                modifier = Modifier.heightIn(min = 35.dp),
                verticalAlignment = when (content.subtitle.isNullOrBlank()) {
                    true -> Alignment.CenterVertically
                    false -> Alignment.Bottom
                },
            ) {
                TopBarNavigation(
                    model = navigation,
                    contentColor = colorContent
                )
                TopBarContent(
                    model = content,
                    contentColor = colorContent
                )
                TopBarActions(
                    withContentTint = actionsWithContentTint,
                    accentColor = colors.accent,
                    contentColor = colorContent,
                    models = actions,
                )
            }

            TopBarSubtitle(
                subtitle = content.subtitle,
                contentColor = colorContent,
                withNavigation = navigation != null
            )
        }
    }
}

@Composable
private fun TopBarSubtitle(
    subtitle: String?,
    withNavigation: Boolean,
    contentColor: Color,
) {
    if (!subtitle.isNullOrBlank()) {
        Text(
            style = typography.body,
            color = contentColor.alphaMedium,
            text = subtitle.decoratedAnnotatedString(),
            modifier = Modifier
                .padding(bottom = dimension.small)
                .padding(
                    horizontal = when (withNavigation) {
                        true -> dimension.semiLarge + dimension.medium
                        false -> dimension.none
                    }
                )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xffffff, widthDp = 360)
@Composable
private fun PreviewDSTopBar() {
    val content = DSTopBarContent(title = titleOfApp, subtitle = titleOfApp)
    val navigation = DSTopBarNavigation(icon = R.drawable.ic_navigation_back, onClick = onClick)
    val actions = listOf(
        DSTopBarAction(icon = R.drawable.ic_system_notification, TRUE, onClick),
        DSTopBarAction(icon = R.drawable.ic_system_search, onClick = onClick),
        DSTopBarAction(icon = R.drawable.ic_system_options_dots, onClick = onClick),
    )

    Column {
        DSTopBar(
            content = content,
            navigation = navigation,
            actions = actions,
            actionsWithContentTint = FALSE,
            colors = DSTopBarUtils.getColors(
                content = color.primarySurface.alphaMedium,
                accent = color.accent,
            )
        )

        DSTopBar(
            navigation = navigation,
            content = content.copy(subtitle = null),
            actionsWithContentTint = TRUE,
            actions = actions.take(2),
            colors = DSTopBarUtils.getColors(
                container = color.primary,
                content = color.onPrimary,
            )
        )
    }
}
