package io.jimmyjossue.designsystemlibrary.components.dialog.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonPrimary
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonSecondary
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonUtils
import io.jimmyjossue.designsystemlibrary.components.dialog.DSDialogUtils
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogButton
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogColors
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogConfig
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogContent
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogOption
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogOptionType
import io.jimmyjossue.designsystemlibrary.components.dialog.model.dsDialogButtonCancel
import io.jimmyjossue.designsystemlibrary.components.dialog.model.dsDialogButtonConfirm
import io.jimmyjossue.designsystemlibrary.components.separator.DSDividerHorizontal
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaSemiLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.extension.borderBottom
import io.jimmyjossue.designsystemlibrary.utils.onClick
import io.jimmyjossue.designsystemlibrary.utils.textdecorator.decoratedAnnotatedString

@Composable
internal fun DSDialogQuestion(
    confirmButton: DSDialogButton,
    cancelButton: DSDialogButton,
    colors: DSDialogColors = DSDialogUtils.getColors(),
    config: DSDialogConfig = DSDialogUtils.getConfig(),
    onDismiss: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimension.medium)
    ) {
        DSButtonSecondary(
            text = cancelButton.text,
            isEnabled = cancelButton.isEnabled,
            modifier = Modifier.weight(weight = 1f),
            shape = shape.small,
            colors = DSButtonUtils.getSecondaryColors(
                content = colors.typography.alphaHigh,
                border = colors.typography.alphaLow
            ),
            onClick = {
                cancelButton.onClick()
                onDismiss()
            },
        )
        DSButtonPrimary(
            text = confirmButton.text,
            isEnabled = confirmButton.isEnabled,
            modifier = Modifier.weight(weight = 1f),
            shape = shape.small,
            colors = DSButtonUtils.getPrimaryColors(
                content = colors.onPrimary,
                background = colors.primary,
                contentDisabled = colors.primaryDisabled,
                backgroundDisabled = colors.primaryDisabled.alphaSemiLow,
            ),
            onClick = {
                confirmButton.onClick()
                onDismiss()
            },
        )
    }
}

@Composable
internal fun DSDialogOptions(
    data: DSDialogContent.Options,
    colors: DSDialogColors = DSDialogUtils.getColors(),
    config: DSDialogConfig = DSDialogUtils.getConfig(),
    onDismiss: () -> Unit,
 ) {
    val selected = remember { mutableStateOf<DSDialogOption?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        data.options.forEachIndexed { index, option ->
            DialogOptionItem(
                isSelected = selected.value == option,
                option = option,
                type = data.type,
                colors = colors,
                isLast = index < data.options.lastIndex,
                onClick = { selected.value = option }
            )
        }
    }
    DSDialogQuestion(
        onDismiss = onDismiss,
        cancelButton = data.cancelButton,
        config = config,
        confirmButton = data.confirmButton.copy(
            isEnabled = selected.value != null
        ),
    )
}

@Composable
private fun DialogOptionItem(
    isSelected: Boolean,
    option: DSDialogOption,
    colors: DSDialogColors,
    type: DSDialogOptionType,
    isLast: Boolean = false,
    onClick: (DSDialogOption) -> Unit
) {
    fun Boolean.getIcon() = if (this) R.drawable.ds_ic_system_radio_on_light else R.drawable.ds_ic_system_radio_off_light

    @Composable
    fun Boolean.getColor() = if (this) colors.primary else colors.typography

    Button(
        modifier = when (isLast) {
            true -> Modifier.borderBottom(color = colors.surface)
            false -> Modifier
        },
        onClick = { onClick(option) },
        shape = shape.smalled,
        contentPadding = PaddingValues(horizontal = dimension.smalled, vertical = dimension.small),
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = colors.typography,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = colors.typography,
        ),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = dimension.large - dimension.medium),
                horizontalArrangement = Arrangement.spacedBy(dimension.medium),
                verticalAlignment = when (option.caption.isNullOrBlank()) {
                    true -> Alignment.CenterVertically
                    false -> Alignment.Top
                }
            ) {
                Icon(
                    painter = painterResource(id = isSelected.getIcon()),
                    tint = isSelected.getColor(),
                    contentDescription = null
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(dimension.smalled),
                ) {
                    Text(
                        text = option.text.decoratedAnnotatedString(),
                        style = typography.body,
                        color = colors.typography.alphaHigh,
                    )

                    if (!option.caption.isNullOrBlank()) {
                        Text(
                            text = option.caption.orEmpty().decoratedAnnotatedString(),
                            style = typography.caption,
                            color = colors.typography.alphaSemiLow,
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewDSAlertQuestion() {
    val config = DSDialogUtils.getConfig(imageFraction = 0.5f)
    val dataQuestion = DSDialogContent.Question(
        image = R.drawable.ic_system_file_image,
        title = "Borrar archivo",
        body = "Al borrar este archivo recuerda que tendras 7 días para recuperarlo. Despues de eso se eliminara permanentemente.",
        confirmButton = dsDialogButtonConfirm(onClick = onClick),
        cancelButton = dsDialogButtonCancel(onClick = onClick),
    )
    val dataOption = DSDialogContent.Options(
        image = R.drawable.ic_system_file_image,
        title = "Borrar archivo",
        body = "Al borrar este archivo recuerda que tendras 7 días para recuperarlo. Despues de eso se eliminara permanentemente.",
        options = listOf(
            object : DSDialogOption {
                override val id = ""
                override val text = "Estados Unidos <sb>México</sb>"
                override val caption = "Lider en exportacion de Maíz"
            }, object : DSDialogOption {
                override val id = ""
                override val text = "Colombia"
                override val caption = "Pura coca"
            }, object : DSDialogOption {
                override val id = ""
                override val text = "Estados Unidos"
                override val caption = null
            }, object : DSDialogOption {
                override val id = ""
                override val text = "Canada"
                override val caption = "Uno de los paises mas frios del norte (la neta no)."
            }, object : DSDialogOption {
                override val id = ""
                override val text = "China"
                override val caption = null
            }
        ),
        confirmButton = dsDialogButtonConfirm(onClick = onClick),
        cancelButton = dsDialogButtonCancel(onClick = onClick),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        //DialogContent(contentData = dataQuestion, config = config) {
        //    DSDialogQuestion(
        //        config = config,
        //        data = dataQuestion,
        //        onDismiss = onClick
        //    )
        //}
        DialogContent(contentData = dataOption, config = config) {
            DSDialogOptions(
                config = config,
                data = dataOption,
                onDismiss = onClick
            )
        }
    }
}
