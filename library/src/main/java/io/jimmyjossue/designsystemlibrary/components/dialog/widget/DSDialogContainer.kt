package io.jimmyjossue.designsystemlibrary.components.dialog.widget

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.dialog.DSDialogUtils
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogColors
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogConfig
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogContent
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogOption
import io.jimmyjossue.designsystemlibrary.components.dialog.model.dsDialogButtonCancel
import io.jimmyjossue.designsystemlibrary.components.dialog.model.dsDialogButtonConfirm
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaSemiLow
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.onClick
import io.jimmyjossue.designsystemlibrary.utils.textdecorator.decoratedAnnotatedString

@Composable
internal fun DSDialogContainer(
    onDismiss: () -> Unit,
    contentData: DSDialogContent,
    colors: DSDialogColors = DSDialogUtils.getColors(),
    config: DSDialogConfig = DSDialogUtils.getConfig(),
    content: @Composable (ColumnScope.() -> Unit)
) {
    BackHandler(
        enabled = config.isEnableBackHandler.not(),
        onBack = onClick
    )

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.scrim)
                .clickable(enabled = false, onClick = onClick, role = Role.Image),
        ) {
            Box(contentAlignment = Alignment.TopEnd) {

                DialogContent(
                    contentData = contentData,
                    content = content,
                    config = config,
                )

                if (contentData is DSDialogContent.Info) {
                    IconButton(
                        modifier = Modifier
                            .padding(all = dimension.semiLarge)
                            .size(size = dimension.semiLarge + dimension.small),
                        colors = IconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = colors.typography.alphaSemiLow,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = colors.typography.alphaSemiLow,
                        ),
                        onClick = onDismiss,
                    ) {
                        Icon(
                            modifier = Modifier.size(dimension.medium + dimension.smalled),
                            painter = painterResource(id = R.drawable.ds_ic_action_close_light),
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun DialogContent(
    contentData: DSDialogContent,
    colors: DSDialogColors = DSDialogUtils.getColors(),
    config: DSDialogConfig = DSDialogUtils.getConfig(),
    content: @Composable (ColumnScope.() -> Unit)
) {
    Column(
        modifier = Modifier
            .padding(dimension.medium)
            .fillMaxWidth()
            .background(shape = config.dialogShape, color = colors.background)
            .padding(dimension.semiLarge),
        verticalArrangement = Arrangement.spacedBy(dimension.semiLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (contentData.image != null) {
            Image(
                painter = painterResource(id = contentData.image!!),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(fraction = config.imageFraction)
                    .aspectRatio(ratio = 1F)
            )
        }
        if (!contentData.title.isNullOrBlank()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = contentData.title.orEmpty().decoratedAnnotatedString(),
                style = typography.title,
                color = colors.typography,
            )
        }
        if (contentData.body.isNotBlank()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = contentData.body.decoratedAnnotatedString(),
                style = typography.caption,
                color = colors.typography.alphaMedium,
            )
        }

        content()
    }
}

@Preview
@Composable
private fun PreviewDSDialogContainer() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    )
    DSDialogContainer(
        onDismiss = { },
        content = { },
        contentData = DSDialogContent.Info(
            title = "Borrar archivo",
            body = "Al borrar este archivo recuerda que tendras 7 días para recuperarlo. Despues de eso se eliminara permanentemente.",
        ),
    )
}