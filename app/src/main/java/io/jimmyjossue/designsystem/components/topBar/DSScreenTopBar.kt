package io.jimmyjossue.designsystem.components.topBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import io.jimmyjossue.designsystem.R
import io.jimmyjossue.designsystem.utils.titleOfApp

data class DSScreenTopBar(
    val title: String? = null,
    val subtitle: String? = null,
    val image: DSTopBarImage? = null,
    val navigation: DSTopBarNavigation? = null,
    val actions: List<DSTopBarAction> = emptyList(),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun screenTopBar(
    model: DSScreenTopBar?,
    scrollBehavior: TopAppBarScrollBehavior,
) = @Composable {
    if (model != null) {
        DSTopBar(
            scrollBehavior = scrollBehavior,
            navigation = model.navigation,
            actions = model.actions,
            content = DSTopBarContent(
                title = model.title,
                subtitle = model.subtitle,
                image = model.image,
            ),
        )
    }
}
