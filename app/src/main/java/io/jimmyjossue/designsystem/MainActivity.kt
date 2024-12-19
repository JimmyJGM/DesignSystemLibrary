package io.jimmyjossue.designsystem

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.jimmyjossue.designsystemlibrary.R.drawable
import io.jimmyjossue.designsystemlibrary.components.card.DSCardInfoType
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBarAction
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBarNavigation
import io.jimmyjossue.designsystemlibrary.template.form.DSFormUtils
import io.jimmyjossue.designsystemlibrary.template.form.PreviewDSForm
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.screen.DSScreen
import io.jimmyjossue.designsystemlibrary.template.screen.DSScreenActions
import io.jimmyjossue.designsystemlibrary.template.screen.DSScreenScope
import io.jimmyjossue.designsystemlibrary.template.screen.DSScreenUtils
import io.jimmyjossue.designsystemlibrary.template.screen.DSScreenUtils.toFormColors
import io.jimmyjossue.designsystemlibrary.template.screen.model.DSScreenTopBar
import io.jimmyjossue.designsystemlibrary.theme.AppTheme
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Screen()
            }
        }
    }
}

@Preview
@Composable
private fun Screen() {
    val loadingState = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val activity = LocalContext.current as Activity
    val screenColors = DSScreenUtils.getColors(
        background = color.surface,
        topBarBackground = color.surface,
        topBarBackgroundScrolled = color.background,
        loaderBackground = color.primarySurface,
    )
    val screenScope = remember { mutableStateOf<DSScreenScope?>(null) }

    fun onShowLoader() {
        scope.launch {
            loadingState.value = true
            delay(2_500L)
            loadingState.value = false
        }
    }

    DSScreen(
        isLoading = loadingState.value,
        colors = screenColors,
        topBar = DSScreenTopBar(
            title = "Crea tu perfil para la aplicación",
            subtitle = "Tus datos están siempre protejidos.",
            actions = listOf(
                DSTopBarAction(
                    hasNotification = true,
                    icon = drawable.ic_system_notification,
                    onClick = { }
                ),
                DSTopBarAction(
                    icon = drawable.ic_system_options_dots,
                    onClick = { }
                )
            ),
            navigation = DSTopBarNavigation(
                icon = drawable.ic_navigation_back,
                //onClick = activity::finish
                onClick = {
                    screenScope.value?.getActions()?.showSnack(
                        message = "Esta es una snack bar informativa",
                        cardType = DSCardInfoType.SUCCESS,
                        icon = drawable.ic_system_notification,
                    )
                }
            )
        ),
        content = {
            LaunchedEffect(screenScope.value) {
                if (screenScope.value == null) {
                    screenScope.value = it
                }
            }
            ScreenContent(
                colors = it.getColors().copy().toFormColors(
                    surface = color.background
                ),
                onShowLoader = ::onShowLoader,
            )
        }
    )
}

@Composable
private fun ScreenContent(
    colors: DSFormColors = DSFormUtils.getColors(),
    onShowLoader: () -> Unit,
) {
    PreviewDSForm(
        onSubmit = onShowLoader,
        colors = colors
    )
}