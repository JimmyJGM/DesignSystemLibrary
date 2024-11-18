package io.jimmyjossue.designsystemlibrary.template.screen.model

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBar
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBarAction
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBarColors
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBarContent
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBarImage
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBarNavigation

data class DSScreenTopBar(
    val title: String? = null,
    val subtitle: String? = null,
    val image: DSTopBarImage? = null,
    val navigation: DSTopBarNavigation? = null,
    val actionsWithContentTint: Boolean = true,
    val actions: List<DSTopBarAction> = emptyList(),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun screenTopBar(
    model: DSScreenTopBar?,
    scrollBehavior: TopAppBarScrollBehavior,
    colors: DSTopBarColors,
) = @Composable {
    if (model != null) {
        DSTopBar(
            colors = colors,
            navigation = model.navigation,
            actions = model.actions,
            scrollBehavior = scrollBehavior,
            actionsWithContentTint = model.actionsWithContentTint,
            content = DSTopBarContent(
                title = model.title,
                subtitle = model.subtitle,
                image = model.image,
            ),
        )
    }
}
