package io.jimmyjossue.designsystem

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.jimmyjossue.designsystemlibrary.R.drawable
import io.jimmyjossue.designsystemlibrary.components.bottombar.PreviewBottomBar
import io.jimmyjossue.designsystemlibrary.components.card.DSCardInfoType
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogButton
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogContent
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogOption
import io.jimmyjossue.designsystemlibrary.components.loading.DSDotsLoadingAnimation
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacer
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBarAction
import io.jimmyjossue.designsystemlibrary.components.topBar.DSTopBarNavigation
import io.jimmyjossue.designsystemlibrary.template.activity.DSActivity
import io.jimmyjossue.designsystemlibrary.template.activity.model.DSActivityArgs
import io.jimmyjossue.designsystemlibrary.template.form.DSFormUtils
import io.jimmyjossue.designsystemlibrary.template.form.PreviewDSForm
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.screen.DSScreen
import io.jimmyjossue.designsystemlibrary.template.screen.DSScreenUtils
import io.jimmyjossue.designsystemlibrary.template.screen.DSScreenUtils.toFormColors
import io.jimmyjossue.designsystemlibrary.template.screen.model.DSScreenScope
import io.jimmyjossue.designsystemlibrary.template.screen.model.DSScreenTopBar
import io.jimmyjossue.designsystemlibrary.template.screen.model.rememberScreenLoaderState
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : DSActivity() {

    @Composable
    private fun Btn(text: String, onClick: () -> Unit) {
        Button(
            onClick = onClick,
            content = { Text(text = text) }
        )
    }

    @Composable
    override fun SetContentView(args: DSActivityArgs) {
        val loaderState = rememberScreenLoaderState()
        val travelDistance = remember { mutableFloatStateOf(0.08f) }
        val sizeDots = remember { mutableFloatStateOf(0.08f) }
        val spaceDots = remember { mutableFloatStateOf(0.06f) }

        LaunchedEffect(loaderState.state.value) {
            delay(3500)
            loaderState.hideLoader()
        }

        PreviewBottomBar(
            title = "Navega sin limites...",
            buttonText = "Agregar un datos chidongongo",
            onClick = ::launchSecondActivity,
        )

        /*
        DSScreen(
            loaderState = loaderState,
            colors = DSScreenUtils.getColors(
                background = color.primarySurface
            ),
            topBar = DSScreenTopBar(
                title = null,
                subtitle = null,
                image = null,
                navigation = null,
                actionsWithContentTint = true,
                actions = emptyList(),
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimension.large),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Btn(text = "Open form!!!", onClick = ::launchSecondActivity)
                //Btn(text = "loadder", onClick = loaderState::showLoader)
                DSSpacer(size = 24.dp)

                DSDotsLoadingAnimation(
                    travelDistance = travelDistance.floatValue.roundSliderValue().dp,
                    dotsSize = sizeDots.floatValue.roundSliderValue().dp,
                    dotsSpace = spaceDots.floatValue.roundSliderValue().dp
                )

                DSSpacer(size = 96.dp)
                Text(text = "travel distance: ${travelDistance.floatValue.roundSliderValue()}.dp")
                Slider(
                    value = travelDistance.floatValue,
                    onValueChange = { travelDistance.floatValue = it }
                )
                DSSpacer(size = 8.dp)
                Text(text = "sizeDots: ${sizeDots.floatValue.roundSliderValue()}.dp")
                Slider(
                    value = sizeDots.floatValue,
                    onValueChange = { sizeDots.floatValue = it }
                )
                DSSpacer(size = 8.dp)
                Text(text = "spaceDots: ${spaceDots.floatValue.roundSliderValue()}.dp")
                Slider(
                    value = spaceDots.floatValue,
                    onValueChange = { spaceDots.floatValue = it }
                )
            }
        }
        */
    }

    fun Float.roundSliderValue() = when (this == 1F) {
        true -> 100
        false -> (this * 100).toInt() % 100
    }

    private fun launchSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }
}

class SecondActivity : DSActivity() {
    @Composable
    override fun SetContentView(args: DSActivityArgs) {
        Screen()
    }
}

private val titleDialog = "Borrar archivo"
private val bodyDialog = "Al borrar este archivo recuerda que tendras 7 días para recuperarlo. Despues de ese tiempo se eliminara permanentemente."
private val confirmButtonDialog = DSDialogButton(text = "Confirmar", onClick = {}, isEnabled = true)
private val cancelButtonDialog = DSDialogButton(text = "Cancelar", onClick = { }, isEnabled = true)
private val optionsDialog = listOf(
    object : DSDialogOption {
        override val id = "123334"
        override val text = "Estados Unidos <sb>México</sb>"
        override val caption = "Lider en exportacion de Maíz"
    },
    object : DSDialogOption {
        override val id = "123334"
        override val text = "Colombia"
        override val caption = "Pura coca"
    },
    object : DSDialogOption {
        override val id = "123334"
        override val text = "Estados Unidos"
        override val caption = null
    },
    object : DSDialogOption {
        override val id = "123334"
        override val text = "Canada"
        override val caption = "Uno de los paises mas frios del norte (la neta no)."
    },
    object : DSDialogOption {
        override val id = "123334"
        override val text = "China"
        override val caption = null
    },
    object : DSDialogOption {
        override val id = "123334"
        override val text = "China"
        override val caption = null
    },
    object : DSDialogOption {
        override val id = "123334"
        override val text = "Namek"
        override val caption = "Planeta de picoro"
    },
    object : DSDialogOption {
        override val id = "123334"
        override val text = "Argentina"
        override val caption = "mejor país del mundo mundial."
    }
)

@Preview
@Composable
private fun Screen(
    modifier: Modifier = Modifier
) {
    val loadingState = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val screenScope = remember { mutableStateOf<DSScreenScope?>(null) }
    val screenColors = DSScreenUtils.getColors(
        loaderBackground = color.primarySurface,
    )

    fun onShowLoader() {
        scope.launch {
            loadingState.value = true
            delay(3_500L)
            loadingState.value = false
        }
    }

    fun onShowDialog(type: Int) {
        Log.d("onShowDialog", "onShowDialog")
        Log.d("screenScope", "${screenScope.value}")
        Log.d("actions", "${screenScope.value?.getActions()}")
        Log.d("type", "$type")

        screenScope.value?.getActions()?.showDialog(
            when (type) {
                1 -> DSDialogContent.Info(
                    title = titleDialog,
                    body = bodyDialog,
                )
                2 -> DSDialogContent.Question(
                    title = titleDialog,
                    body = bodyDialog,
                    cancelButton = cancelButtonDialog,
                    confirmButton = confirmButtonDialog,
                )
                else -> DSDialogContent.Options(
                    title = titleDialog,
                    body = bodyDialog,
                    cancelButton = cancelButtonDialog,
                    confirmButton = confirmButtonDialog,
                    options = optionsDialog
                )
            }
        )
    }

    fun showAlert(cardType: DSCardInfoType = DSCardInfoType.INFORMATION) {
        val state = when (cardType) {
            DSCardInfoType.SUCCESS -> "success"
            DSCardInfoType.INFORMATION -> "info"
            DSCardInfoType.WARNING -> "warning"
            DSCardInfoType.ERROR -> "error"
        }
        val icon = when (cardType) {
            DSCardInfoType.SUCCESS -> drawable.ic_state_success
            DSCardInfoType.INFORMATION -> drawable.ic_state_information
            DSCardInfoType.WARNING -> drawable.ic_state_warning
            DSCardInfoType.ERROR -> drawable.ic_state_error
        }
        screenScope.value?.getActions()?.showSnack(
            message = "Esta es una <b>snack bar</b> de estado <$state>$state</$state>",
            cardType = cardType,
            icon = icon,
        )
    }

    DSScreen(
        modifier = modifier,
        colors = screenColors,
        loaderState = rememberScreenLoaderState(),
        topBar = DSScreenTopBar(
            title = "Crea tu perfil para la aplicación",
            subtitle = "Tus datos están siempre protejidos.",
            actions = listOf(
                DSTopBarAction(
                    hasNotification = true,
                    icon = drawable.ic_system_notification,
                    onClick = { showAlert(cardType = DSCardInfoType.WARNING) }
                ),
                DSTopBarAction(
                    icon = drawable.ic_system_options_dots,
                    onClick = { showAlert(cardType = DSCardInfoType.ERROR) }
                )
            ),
            navigation = DSTopBarNavigation(
                icon = drawable.ic_navigation_back,
                //onClick = activity::finish
                onClick = {
                    onShowDialog((1..5).random())
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
                colors = it.getColors().toFormColors(
                    surface = color.surface
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

@Preview(backgroundColor = 0xFFF, showBackground = true)
@Composable
private fun PreviewText() {
    var layout by remember { mutableStateOf<TextLayoutResult?>(null) }

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            modifier = Modifier.drawBehind {
                val strokeWidthPx = 1.dp.toPx()
                val verticalOffset = size.height - 2.sp.toPx()
                drawLine(
                    color = Color.Magenta,
                    strokeWidth = strokeWidthPx,
                    start = Offset(0f, verticalOffset),
                    end = Offset(size.width, verticalOffset),
                    pathEffect = pathEffect
                )
            },
            text = "i izu gxosg cou dou oudho cvgoudhoufdg vcoud ogdcou odov hflg ouhfou vod hofoc hochoufouof!!!"
        )
        Text(
            text = buildAnnotatedString {
                append("enlace ")
                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = Color.Red
                    )
                ) {
                    append("click her!!!")
                }
            }
        )
    }
}
