package io.jimmyjossue.designsystemlibrary.template.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import io.jimmyjossue.designsystemlibrary.components.card.screenSnackBar
import io.jimmyjossue.designsystemlibrary.components.dialog.DSDialog
import io.jimmyjossue.designsystemlibrary.components.loading.DSLoaderIndeterminateScreen
import io.jimmyjossue.designsystemlibrary.template.screen.DSScreenUtils.toTopBarColors
import io.jimmyjossue.designsystemlibrary.template.screen.model.DSScreenColors
import io.jimmyjossue.designsystemlibrary.template.screen.model.DSScreenLoader
import io.jimmyjossue.designsystemlibrary.template.screen.model.DSScreenScope
import io.jimmyjossue.designsystemlibrary.template.screen.model.DSScreenTopBar
import io.jimmyjossue.designsystemlibrary.template.screen.model.DSScrollBehaviorType
import io.jimmyjossue.designsystemlibrary.template.screen.model.screenTopBar
import io.jimmyjossue.designsystemlibrary.template.screen.model.toScrollBehavior

internal val LocalScreenScope = staticCompositionLocalOf<DSScreenScope?> { null }
private val defaultModifier = Modifier.fillMaxSize()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DSScreen(
    modifier: Modifier = defaultModifier,
    topBar: DSScreenTopBar? = null,
    isLoading: Boolean = false,
    loader: DSScreenLoader = DSScreenLoader(),
    scrollBehaviorType: DSScrollBehaviorType = DSScrollBehaviorType.PINNED,
    colors: DSScreenColors = DSScreenUtils.getColors(),
    content: @Composable (DSScreenScope) -> Unit
) {
    val actions = DSScreenActions(
        snackbarHostState = remember { SnackbarHostState() },
        coroutineScope = rememberCoroutineScope(),
        scrollBehavior = scrollBehaviorType.toScrollBehavior(),
        focusManager = LocalFocusManager.current,
        keyboardController = LocalSoftwareKeyboardController.current
    )

    val scope = object : DSScreenScope {
        override fun getColors() = colors
        override fun getActions() = actions
    }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            scope.getActions().hideKeyboard()
        }
    }

    CompositionLocalProvider(LocalScreenScope provides scope) {
        Scaffold(
            modifier = modifier
                .background(color = scope.getColors().background)
                .nestedScroll(connection = scope.getActions().scrollBehavior.nestedScrollConnection)
                .getScreenStatusBarPaddings(haveTopBar = topBar != null)
                .getScreenNavBarPaddings(haveNavBar = false)
                .imePadding(),
            snackbarHost = screenSnackBar(hostState = scope.getActions().snackbarHostState),
            contentWindowInsets = WindowInsets.safeDrawing,
            containerColor = scope.getColors().background,
            contentColor = scope.getColors().content,
            topBar = screenTopBar(
                scrollBehavior = scope.getActions().scrollBehavior,
                colors = scope.getColors().toTopBarColors(),
                model = topBar,
            ),
            content = {
                Box(
                    modifier = Modifier.padding(it),
                    content = { content(scope) }
                )
            }
        )

        scope.getActions().dialogContent.value?.let { dialogContent ->
            DSDialog(
                content = dialogContent,
                onDismiss = { scope.getActions().hideDialog() }
            )
        }

        DSLoaderIndeterminateScreen(
            isVisible = isLoading,
            text = loader.text,
            primaryColor = colors.loaderPrimary,
            backgroundColor = colors.loaderBackground,
            isEnableBackHandler = loader.isEnableBackHandler,
        )
    }
}

private fun Modifier.getScreenStatusBarPaddings(
    haveTopBar: Boolean
): Modifier {
    return when (!haveTopBar) {
        true -> this.statusBarsPadding()
        false -> this
    }
}

private fun Modifier.getScreenNavBarPaddings(
    haveNavBar: Boolean
): Modifier {
    return when (haveNavBar) {
        true -> this.navigationBarsPadding()
        false -> this
    }
}
