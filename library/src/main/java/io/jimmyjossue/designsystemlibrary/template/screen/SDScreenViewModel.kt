package io.jimmyjossue.designsystemlibrary.template.screen

import androidx.lifecycle.ViewModel
import io.jimmyjossue.designsystemlibrary.components.dialog.model.DSDialogContent
import io.jimmyjossue.designsystemlibrary.template.screen.model.DSScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class SDScreenViewModel : ViewModel() {

    private val _stateUi = MutableStateFlow(DSScreenUiState())
    val stateUi = _stateUi.asStateFlow()
    private val state = _stateUi.value

    private fun managerLoader(isVisible: Boolean) = _stateUi.update {
        state.copy(isLoading = isVisible)
    }

    private fun managerDialog(content: DSDialogContent? = null) = _stateUi.update {
        state.copy(dialogContent = content)
    }

    fun showLoader() = managerLoader(isVisible = true)

    fun hideLoader() = managerLoader(isVisible = false)

    fun showDialog(content: DSDialogContent) = managerDialog(content = content)

    fun hideDialog() = managerDialog(content = null)
}
