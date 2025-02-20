package io.jimmyjossue.designsystemlibrary.template.screen.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun rememberScreenLoaderState(isEnableBack: Boolean = false) = remember {
    DSScreenLoaderState(isEnableBack = isEnableBack)
}

data class DSLoader(
    val text: String? = null,
    val isLoading: Boolean = false,
    val isEnableBack: Boolean = true,
)

class DSScreenLoaderState(
    isEnableBack: Boolean = false
) {
    var state = mutableStateOf(value = DSLoader(isEnableBack = isEnableBack))
        private set

    private fun managerLoader(
        isLoading: Boolean,
        text: String? = state.value.text
    ) {
        state.value = state.value.copy(
            isLoading = isLoading,
            text = text
        )
    }

    fun showLoader(
        text: String? = state.value.text
    ) = managerLoader(
        isLoading = true,
        text = text,
    )

    fun hideLoader() = managerLoader(isLoading = false)

    fun setText(value: String?) {
        state.value = state.value.copy(text = value)
    }
}
