package io.jimmyjossue.designsystem.templetes.screen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystem.components.card.DSCardInfoType
import io.jimmyjossue.designsystem.components.card.DSSizeType
import io.jimmyjossue.designsystem.components.card.DSSnackBarVisuals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DSScreenUtils @OptIn(ExperimentalMaterial3Api::class) constructor(
    internal val snackbarHostState: SnackbarHostState,
    internal val coroutineScope: CoroutineScope,
    internal val scrollBehavior: TopAppBarScrollBehavior,
) {
    fun showSnack(
        duration: SnackbarDuration = SnackbarDuration.Short,
        message: String,
        withDismissAction: Boolean = true,
        icon: Int? = null,
        sizeType: DSSizeType = DSSizeType. NORMAL,
        cardType: DSCardInfoType = DSCardInfoType. INFORMATION,
        cardShape: Shape = RoundedCornerShape(8.dp),
        onClick: (() -> Unit)? = null
    ) {
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                visuals = DSSnackBarVisuals(
                    actionLabel = null,
                    duration = duration,
                    message = message,
                    withDismissAction = withDismissAction,
                    icon = icon,
                    sizeType = sizeType,
                    cardType = cardType,
                    cardShape = cardShape,
                    onClick = onClick,
                )
            )
        }
    }
}
