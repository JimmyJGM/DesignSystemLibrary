package io.jimmyjossue.designsystemlibrary.template.screen.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.jimmyjossue.designsystemlibrary.template.screen.model.DSScrollBehaviorType
import io.jimmyjossue.designsystemlibrary.template.screen.model.DSScrollBehaviorType.PINNED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun rememberScreenState(
    scrollBehaviorType: DSScrollBehaviorType = PINNED
): DSScreenState {
    return remember {
        DSScreenState(
            scrollBehaviorType = scrollBehaviorType,
        )
    }
}

class DSScreenState(
    val scrollBehaviorType: DSScrollBehaviorType = PINNED
) {
    private val _loaderState = MutableStateFlow(value = false)
    val loaderState = _loaderState.asStateFlow()

    fun managerLoader(isLoading: Boolean) = _loaderState.update { isLoading }


}