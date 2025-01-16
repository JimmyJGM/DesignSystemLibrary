package io.jimmyjossue.designsystemlibrary.components.dialog

import androidx.compose.runtime.Composable
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogColors
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogConfig
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogContent
import io.jimmyjossue.designsystemlibrary.components.dialog.widget.DSDialogContainer
import io.jimmyjossue.designsystemlibrary.components.dialog.widget.DSDialogOptions
import io.jimmyjossue.designsystemlibrary.components.dialog.widget.DSDialogQuestion

@Composable
fun DSDialog(
    content: DSDialogContent,
    colors: DSDialogColors = DSDialogUtils.getColors(),
    config: DSDialogConfig = DSDialogUtils.getConfig(),
    onDismiss: () -> Unit,
) {
    DSDialogContainer(
        onDismiss = onDismiss,
        colors = colors,
        config = config,
        contentData = content
    ) {
        when (content) {
            is DSDialogContent.Info -> Unit
            is DSDialogContent.Options -> {
                DSDialogOptions(
                    data = content,
                    config = config,
                    colors = colors,
                    onDismiss = onDismiss,
                )
            }
            is DSDialogContent.Question -> {
                DSDialogQuestion(
                    confirmButton = content.confirmButton,
                    cancelButton = content.cancelButton,
                    onDismiss = onDismiss,
                )
            }
        }
    }
}
