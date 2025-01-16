package io.jimmyjossue.designsystemlibrary.template.screen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import io.jimmyjossue.designsystemlibrary.components.card.DSCardInfoType
import io.jimmyjossue.designsystemlibrary.components.card.DSSizeType
import io.jimmyjossue.designsystemlibrary.components.card.DSSnackBarVisuals
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DSScreenActions @OptIn(ExperimentalMaterial3Api::class) constructor(
    internal val snackbarHostState: SnackbarHostState,
    internal val coroutineScope: CoroutineScope,
    internal val keyboardController: SoftwareKeyboardController?,
    internal val scrollBehavior: TopAppBarScrollBehavior,
    internal val focusManager: FocusManager,
) {
    val dialogContent: MutableState<DSDialogContent?> = mutableStateOf(null)

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

    fun hideKeyboard() {
        keyboardController?.hide()
        keyboardController
        focusManager.clearFocus()
    }

    fun showDialog(content: DSDialogContent) {
        dialogContent.value = content
    }

    fun hideDialog() {
        dialogContent.value = null
    }
}
