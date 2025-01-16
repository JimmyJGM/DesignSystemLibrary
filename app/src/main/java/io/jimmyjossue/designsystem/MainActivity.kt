package io.jimmyjossue.designsystem

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import io.jimmyjossue.designsystemlibrary.components.card.DSCardInfoType
import io.jimmyjossue.designsystemlibrary.components.dialog.DSDialog
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogButton
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogContent
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogOption
import io.jimmyjossue.designsystemlibrary.components.dialog.model.dsDialogButtonCancel
import io.jimmyjossue.designsystemlibrary.components.dialog.model.dsDialogButtonConfirm
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
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : DSActivity() {

    @Composable
    override fun SetContentView(args: DSActivityArgs) {
        DSScreen(
            colors = DSScreenUtils.getColors(
                background = color.primarySurface
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = ::launchSecondActivity,
                    content = {
                        Text(text = "Open!!!")
                    }
                )
            }
        }
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
            delay(2_500L)
            loadingState.value = false
        }
    }

    fun onShowDialog(type: Int) {
        scope.launch {
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
                    else ->  DSDialogContent.Options(
                        title = titleDialog,
                        body = bodyDialog,
                        cancelButton = cancelButtonDialog,
                        confirmButton = confirmButtonDialog,
                        options = optionsDialog
                    )
                }
            )
        }
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
        isLoading = loadingState.value,
        colors = screenColors,
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
