package io.jimmyjossue.designsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonPrimary
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonSecondary
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonUtils
import io.jimmyjossue.designsystemlibrary.components.input.DSInputText
import io.jimmyjossue.designsystemlibrary.components.loading.DSLoaderIndeterminateScreen
import io.jimmyjossue.designsystemlibrary.template.screen.DSScreen
import io.jimmyjossue.designsystemlibrary.template.screen.DSScreenUtils
import io.jimmyjossue.designsystemlibrary.theme.AppTheme
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLower
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val decorFitsSystemWindows = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, decorFitsSystemWindows)
        enableEdgeToEdge()

        setContent {
            //SetStatusBarColor(background = Color.Transparent)
            AppTheme {
                Content()
            }
        }
    }
}

private val colorPink = Color(0xFFFFE3E3)
private val colorBlue = Color(0xF0E9FFFF)
private val colorYellow = Color(0xFFFFF7D2)
private val colorSurface = Color(0xFFF3F5F5)
private val colorPrimary = Color(0xFF0086E8)
private val colorPrimarySurface = Color(0xFFCAE8FF)

@Composable
private fun SurfaceBox(
    modifier: Modifier,
    shapeBox: Shape = shape.small,
) {
    Box(
        modifier = Modifier
            .clip(shape = shapeBox)
            .background(color = color.surface, shape = shapeBox)
            .border(width = 1.dp, color = color.surfaceDark.alphaLow, shape = shapeBox)
            .then(other = modifier),
    )
}

@Composable
private fun Content() {
    val textValueState = remember { mutableStateOf("Cargando...") }
    val loadingState = remember { mutableStateOf(false) }
    val backgroundColor = remember { mutableStateOf(Color.White) }
    val scope = rememberCoroutineScope()

    fun changeColor() {
        when (backgroundColor.value) {
            Color.White -> backgroundColor.value = colorPink
            colorPink -> backgroundColor.value = colorBlue
            colorBlue -> backgroundColor.value = colorYellow
            colorYellow -> backgroundColor.value = colorSurface
            colorSurface -> backgroundColor.value = colorPrimary
            colorPrimary -> backgroundColor.value = colorPrimarySurface
            colorPrimarySurface -> backgroundColor.value = Color.White
        }
    }

    fun showLoader() {
        scope.launch {
            loadingState.value = true
            delay(10_000L)
            loadingState.value = false
        }
    }

    DSScreen(
        isLoading = loadingState.value,
        colors = DSScreenUtils.getColors(
            content = color.onPrimary,
            background = backgroundColor.value,
        ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimension.medium)
                    .verticalScroll(state = rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(dimension.semiLarge)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(dimension.small)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(dimension.small)) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(dimension.small),
                            modifier = Modifier
                                .weight(1F)
                                .height(height = dimension.extraLarge)
                        ) {
                            SurfaceBox(modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f))
                            SurfaceBox(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(dimension.semiLarge)
                            )
                        }
                        SurfaceBox(modifier = Modifier
                            .weight(1f)
                            .height(dimension.extraLarge))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(dimension.small)) {
                        SurfaceBox(modifier = Modifier.weight(1.75f).height(dimension.large))
                        Column(
                            verticalArrangement = Arrangement.spacedBy(dimension.small),
                            modifier = Modifier
                                .weight(1F)
                                .height(height = dimension.large)
                        ) {
                            SurfaceBox(modifier = Modifier.fillMaxWidth().weight(1f))
                            SurfaceBox(modifier = Modifier.fillMaxWidth().weight(1.5f))
                        }
                    }
                }

                Text(
                    color = if (backgroundColor.value.luminance() >= 0.5) Color.Black else Color.White,
                    text = "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final."
                )

                DSInputText(
                    value = textValueState.value,
                    onChangeValue = { textValueState.value = it }
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimension.medium)
                ) {
                    DSButtonPrimary(
                        modifier = Modifier.weight(1f),
                        text = "Cambiar color",
                        onClick = ::changeColor,
                    )
                    DSButtonSecondary(
                        text = "Loader",
                        onClick = ::showLoader,
                        colors = DSButtonUtils.getButtonSecondaryColors(
                            border = color.typography,
                            content = color.typography,
                        ),
                    )
                }
            }
        }
    )
}