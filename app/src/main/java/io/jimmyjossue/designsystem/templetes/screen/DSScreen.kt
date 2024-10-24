package io.jimmyjossue.designsystem.templetes.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import io.jimmyjossue.designsystem.components.card.screenSnackBar
import io.jimmyjossue.designsystem.components.topBar.DSScreenTopBar
import io.jimmyjossue.designsystem.components.topBar.screenTopBar
import io.jimmyjossue.designsystem.theme.color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DSScreen(
    topBar: DSScreenTopBar? = null,
    scrollBehaviorType: DSScrollBehaviorType = DSScrollBehaviorType.PINNED,
    content: @Composable (DSScreenUtils) -> Unit
) {
    val utils = DSScreenUtils(
        snackbarHostState = remember { SnackbarHostState() },
        coroutineScope = rememberCoroutineScope(),
        scrollBehavior = scrollBehaviorType.toScrollBehavior(),
    )

    Scaffold(
        modifier = Modifier.nestedScroll(connection = utils.scrollBehavior.nestedScrollConnection),
        topBar = screenTopBar(model = topBar, scrollBehavior = utils.scrollBehavior),
        snackbarHost = screenSnackBar(hostState = utils.snackbarHostState),
        containerColor = color.background,
        contentColor = color.typography,
        content = {
            Box(
                modifier = Modifier.padding(it),
                content = { content(utils) }
            )
        }
    )
}

/*
@Preview
@Composable
private fun PreviewScreen() {
    val scrollState = rememberScrollState()

    DSScreen(
        topBar = DSScreenTopBar(
            title = titleOfApp,
            subtitle = descriptionOfApp,
            navigation = DSTopBarNavigation(iconBack, onClick),
            actions = listOf(DSTopBarAction(iconSearch, FALSE, onClick))
        )
    ) { utils ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ) {
            repeat(times = 15) {
                DSCardInfo(
                    icon = R.drawable.ic_system_notification,
                    shapeCard = RoundedCornerShape(size = radius.medium),
                    message = "<b>Item</b> de lista ${it.plus(other = 1)}",
                    modifier = Modifier
                        .padding(horizontal = dimension.medium)
                        .padding(
                            top = dimension.smalled,
                            bottom = dimension.small + dimension.smalled
                        ),
                    onClick = {
                        utils.showSnack(
                            message = "Mensaje en card del snackbar del item ${it.plus(other = 1)}",
                            icon = R.drawable.ic_system_notification,
                        )
                    }
                )
            }
        }
    }
}
*/
