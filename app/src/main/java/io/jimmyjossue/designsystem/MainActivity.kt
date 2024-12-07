package io.jimmyjossue.designsystem

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import io.jimmyjossue.designsystemlibrary.R.drawable
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBarNavigation
import io.jimmyjossue.designsystemlibrary.template.form.DSFormUtils
import io.jimmyjossue.designsystemlibrary.template.form.PreviewDSForm
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.screen.DSScreen
import io.jimmyjossue.designsystemlibrary.template.screen.DSScreenUtils
import io.jimmyjossue.designsystemlibrary.template.screen.model.DSScreenTopBar
import io.jimmyjossue.designsystemlibrary.theme.AppTheme
import io.jimmyjossue.designsystemlibrary.theme.SetStatusBarColor
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaSemiLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val decorFitsSystemWindows = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, decorFitsSystemWindows)
        enableEdgeToEdge()

        setContent {
            SetStatusBarColor(background = Color.Transparent)
            AppTheme {
                Screen()
            }
        }
    }
}

@Preview
@Composable
private fun Screen() {
    val nameState = remember { mutableStateOf("") }
    val paternalNameState = remember { mutableStateOf("") }
    val maternalNameState = remember { mutableStateOf("") }
    val loadingState = remember { mutableStateOf(false) }
    val colors1 = DSFormUtils.getColors()
    val colors2 = DSFormUtils.getColors(
        typography = color.background,
        primary = color.accent,
        accent = color.primary,
        background = color.typography,
        surface = color.typographyDisabled.alphaSemiLow,
        surfaceDark = color.typographyDisabled,
        onPrimary = color.onPrimary,
        primaryDisabled = color.primaryDisabled
    )
    val colors3 = DSFormUtils.getColors(
        background = color.background,
        surface = color.surface,
        surfaceDark = color.surfaceDark,
    )
    val scope = rememberCoroutineScope()
    val activity = LocalContext.current as Activity
    val colorState = remember { mutableStateOf(colors1) }

    fun onShowLoader() {
        scope.launch {
            loadingState.value = true
            delay(2_500L)
            loadingState.value = false
        }
    }
    fun onChangeColor() {
        colorState.value = when (colorState.value) {
            colors1 -> colors2
            colors2 -> colors3
            else -> colors1
        }
    }

    DSScreen(
        isLoading = loadingState.value,
        topBar = DSScreenTopBar(
            title = "Crea tu perfil",
            navigation = DSTopBarNavigation(
                icon = drawable.ic_navigation_back,
                onClick = activity::finish
            )
        ),
        colors = DSScreenUtils.getColors(
            content = colorState.value.typography,
            background = colorState.value.background,
            topBarBackground = colorState.value.background,
            topBarBackgroundScrolled = colorState.value.surface,
            topBarContent = colorState.value.typography,
            topBarContentScrolled = colorState.value.typography,
            loaderBackground = colorState.value.background,
        ),
        content = {
            ScreenContent(
                name = nameState.value,
                paternalName = paternalNameState.value,
                maternalName = maternalNameState.value,
                colors = colorState.value,
                onShowLoader = {
                    //onShowLoader()
                    onChangeColor()
                },
                onChangeText = { key, value ->
                    when (key) {
                        "name" -> nameState.value = value
                        "paternalName" -> paternalNameState.value = value
                        "maternalName" -> maternalNameState.value = value
                    }
                }
            )
        }
    )
}

@Composable
private fun ScreenContent(
    name: String,
    paternalName: String,
    maternalName: String,
    onChangeText: (key: String, value: String) -> Unit,
    colors: DSFormColors = DSFormUtils.getColors(),
    onShowLoader: () -> Unit,
) {
    PreviewDSForm(
        onSubmit = onShowLoader,
        colors = colors
    )
}