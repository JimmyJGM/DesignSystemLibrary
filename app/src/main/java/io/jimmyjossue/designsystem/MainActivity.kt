package io.jimmyjossue.designsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import io.jimmyjossue.designsystem.components.button.DSButtonPrimary
import io.jimmyjossue.designsystem.components.button.DSButtonSecondary
import io.jimmyjossue.designsystem.components.card.DSCardInfo
import io.jimmyjossue.designsystem.components.card.DSCardInfoType
import io.jimmyjossue.designsystem.components.card.DSCardInfoType.ERROR
import io.jimmyjossue.designsystem.components.card.DSCardInfoType.INFORMATION
import io.jimmyjossue.designsystem.components.card.DSCardInfoType.SUCCESS
import io.jimmyjossue.designsystem.components.card.DSCardInfoType.WARNING
import io.jimmyjossue.designsystem.components.input.DSInputConfig
import io.jimmyjossue.designsystem.components.input.DSInputText
import io.jimmyjossue.designsystem.navigation.NavigationGraph
import io.jimmyjossue.designsystem.theme.AppTheme
import io.jimmyjossue.designsystem.theme.color
import io.jimmyjossue.designsystem.theme.dimension
import io.jimmyjossue.designsystem.theme.shape
import io.jimmyjossue.designsystem.theme.typography
import io.jimmyjossue.designsystem.utils.noneCorner
import io.jimmyjossue.designsystem.utils.withStyle

private const val TITLE = "Título"
private const val CANCEL = "Cancelar"
private const val CONTINUE = "Continuar"
private const val CARD_MESSAGE = "Messages."

private val shapeDialog
    @Composable get() = shape.medium.copy(
        bottomStart = noneCorner,
        bottomEnd = noneCorner
    )

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                NavigationGraph(
                    navController = rememberNavController()
                )
            }
        }
    }
}

@Composable
fun DSMenu(
    onClick: () -> Unit
) {
    val text = remember { mutableStateOf(value = "") }
    val inputError = remember { mutableStateOf(false) }
    val typographies = listOf(typography.header, typography.titleLarge, typography.title, typography.body, typography.caption)
    val types = listOf(INFORMATION, SUCCESS, WARNING, ERROR)
    val onClickFake = { }

    LaunchedEffect(text.value) {
        inputError.value = (text.value == "ERROR")
    }

    AppTheme {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimension.medium),
                modifier = Modifier
                    .background(shape = shapeDialog, color = Color.White)
                    .border(width = dimension.smalled, color = Color.Red, shape = shapeDialog)
                    .padding(all = dimension.medium)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimension.small),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    typographies.forEach {
                        Text(
                            text = TITLE,
                            style = it,
                            color = color.typography
                        )
                    }
                }

                DSInputText(
                    value = text.value,
                    isError = inputError.value,
                    config = DSInputConfig(
                        placeholder = "Escribe tu nombre",
                        label = "Nombre",
                        helper = "No olvides escribir tu nombre",
                    ),
                    onChangeValue = { text.value = it }
                )

                LazyVerticalStaggeredGrid(
                    modifier = Modifier.fillMaxWidth(),
                    state = rememberLazyStaggeredGridState(),
                    horizontalArrangement = Arrangement.spacedBy(dimension.small),
                    verticalItemSpacing = dimension.small,
                    columns = StaggeredGridCells.Fixed(count = 2)
                ) {
                    types.forEach {
                        itemAlert(type = it, icon = R.drawable.ic_state_information)
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = dimension.small)
                ) {
                    DSButtonSecondary(text = CANCEL, onClick = onClick)
                    DSButtonPrimary(text = CONTINUE, onClick = onClickFake)
                }
            }
        }
    }
}

private fun LazyStaggeredGridScope.itemAlert(
    type: DSCardInfoType = INFORMATION,
    @DrawableRes icon: Int? = R.drawable.ic_state_information
) = item {
    DSCardInfo(cardType = type, icon = icon, message = CARD_MESSAGE)
}