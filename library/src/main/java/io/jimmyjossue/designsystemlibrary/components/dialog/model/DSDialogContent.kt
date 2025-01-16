package io.jimmyjossue.designsystemlibrary.components.dialog.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.jimmyjossue.designsystemlibrary.R

sealed class DSDialogContent(
    @DrawableRes open val image: Int?,
    open val title: String?,
    open val body: String,
) {
    data class Info(
        @DrawableRes override val image: Int? = null,
        override val title: String? = null,
        override val body: String,
    ): DSDialogContent(
        image = image,
        title = title,
        body = body
    )

    data class Question(
        @DrawableRes override val image: Int? = null,
        override val title: String? = null,
        override val body: String,
        val confirmButton: DSDialogButton,
        val cancelButton: DSDialogButton,
    ): DSDialogContent(
        image = image,
        title = title,
        body = body
    )

    data class Options(
        @DrawableRes override val image: Int? = null,
        override val title: String? = null,
        override val body: String = "",
        val options: List<DSDialogOption>,
        val confirmButton: DSDialogButton,
        val cancelButton: DSDialogButton,
        val type: DSDialogOptionType = DSDialogOptionType.Check,
    ) : DSDialogContent(
        image = image,
        title = title,
        body = body
    )
}

@Composable
fun dsDialogButtonConfirm(
    text: String = stringResource(id = R.string.ds_label_accept),
    isEnabled: Boolean = true,
    onClick: () -> Unit
) = DSDialogButton(text = text, isEnabled = isEnabled, onClick = onClick)

@Composable
fun dsDialogButtonCancel(
    text: String = stringResource(id = R.string.ds_label_cancel),
    isEnabled: Boolean = true,
    onClick: () -> Unit
) = DSDialogButton(text = text, isEnabled = isEnabled, onClick = onClick)
