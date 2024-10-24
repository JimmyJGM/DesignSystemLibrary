package io.jimmyjossue.designsystem.components.topBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import io.jimmyjossue.designsystem.R
import io.jimmyjossue.designsystem.components.separator.DSDividerHorizontal
import io.jimmyjossue.designsystem.components.topBar.DSTopBarUtils.getFraction
import io.jimmyjossue.designsystem.theme.SetStatusBarColor
import io.jimmyjossue.designsystem.theme.alphaMedium
import io.jimmyjossue.designsystem.theme.dimension
import io.jimmyjossue.designsystem.theme.typography
import io.jimmyjossue.designsystem.utils.borderBottom
import io.jimmyjossue.designsystem.utils.descriptionOfApp
import io.jimmyjossue.designsystem.utils.imageUrlPNG
import io.jimmyjossue.designsystem.utils.onClick
import io.jimmyjossue.designsystem.utils.parseDecoratedAnnotatedString
import io.jimmyjossue.designsystem.utils.titleOfApp

data class DSTopBarColors(
    val content: Color,
    val container: Color,
    val accent: Color,
    val containerScrolled: Color,
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
    val colorContainer by DSTopBarUtils.animateContainerColor(colors, scrollBehavior.getFraction())
    val colorBorder by DSTopBarUtils.animateBorderColor(colors, scrollBehavior.getFraction())
    val heightOffsetLimit = with(LocalDensity.current) { -200.0.dp.toPx() }
    val withIcon = false

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

    SetStatusBarColor(background = colorContainer, isLightIcons = false)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        contentColor = colors.content,
        color = colorContainer,
    ) {
        Column(
            modifier = Modifier
                .borderBottom(color = colorBorder.alphaMedium)
                .padding(
                    start = getPadding(withIcon = withIcon),
                    top = dimension.small,
                    end = getPadding(withIcon = withIcon),
                    bottom = dimension.small,
                ),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimension.medium),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TopBarNavigation(
                    model = navigation,
                    contentColor = colors.content
                )
                TopBarContent(
                    model = content,
                    contentColor = colors.content
                )
                TopBarActions(
                    withContentTint = actionsWithContentTint,
                    accentColor = colors.accent,
                    contentColor = colors.content,
                    models = actions,
                )
            }

            TopBarSubtitle(
                subtitle = content.subtitle,
                contentColor = colors.content,
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
            text = subtitle.parseDecoratedAnnotatedString(),
            modifier = Modifier
                .padding(bottom = dimension.smalled)
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
@Preview(showBackground = true)
@Composable
private fun PreviewDSTopBar() {
    val scroll = TopAppBarDefaults.pinnedScrollBehavior()

    val content = DSTopBarContent(title = titleOfApp, subtitle = descriptionOfApp,)
    val navigation = DSTopBarNavigation(icon = R.drawable.ic_navigation_back, onClick = onClick)
    val actions = listOf(
        DSTopBarAction(icon = R.drawable.ic_system_notification, hasNotification = true, onClick = onClick),
        DSTopBarAction(icon = R.drawable.ic_system_search, onClick = onClick),
        DSTopBarAction(icon = R.drawable.ic_system_options_dots, onClick = onClick),
    )

    Column(
    ) {
        DSTopBar(
            content = content,
            scrollBehavior = scroll,
            navigation = navigation,
            actions = actions,
            actionsWithContentTint = false,
            colors = DSTopBarUtils.getColors()
        )
        DSDividerHorizontal()
        DSTopBar(
            content = content,
            scrollBehavior = scroll,
            actions = actions.reversed().take(n = 1)
        )
        DSDividerHorizontal()
        DSTopBar(
            content = content,
            scrollBehavior = scroll,
            actions = actions.reversed().take(n = 1)
        )
    }
}