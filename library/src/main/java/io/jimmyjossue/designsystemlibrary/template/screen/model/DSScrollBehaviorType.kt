package io.jimmyjossue.designsystemlibrary.template.screen.model

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

enum class DSScrollBehaviorType {
    PINNED,
    ENTER_ALWAYS,
    EXIT_UNTIL_COLLAPSED,
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun DSScrollBehaviorType.toScrollBehavior() = when (this) {
    DSScrollBehaviorType.PINNED -> TopAppBarDefaults.pinnedScrollBehavior()
    DSScrollBehaviorType.ENTER_ALWAYS -> TopAppBarDefaults.enterAlwaysScrollBehavior()
    DSScrollBehaviorType.EXIT_UNTIL_COLLAPSED -> TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
}
