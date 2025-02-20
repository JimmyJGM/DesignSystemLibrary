package io.jimmyjossue.designsystemlibrary.components.step.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.step.config.DSStepStateType
import io.jimmyjossue.designsystemlibrary.components.step.config.DSStepsColors
import io.jimmyjossue.designsystemlibrary.components.step.model.DSStepUtils
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import kotlinx.coroutines.delay

@Composable
internal fun DSStepIcon(
    state: DSStepStateType = DSStepStateType.Initial,
    colors: DSStepsColors = DSStepUtils.getColors()
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(dimension.semiLarge),
    ) {
        when (state) {
            DSStepStateType.Initial -> {
                Box(
                    modifier = Modifier
                        .size(dimension.medium)
                        .background(color = colors.outline, shape = CircleShape)
                )
            }
            DSStepStateType.Progress -> {
                Box(
                    modifier = Modifier
                        .size(dimension.medium)
                        .border(
                            width = dimension.smalled,
                            color = colors.primary,
                            shape = CircleShape
                        )
                )
            }
            DSStepStateType.Done -> {
                Icon(
                    painter = painterResource(id = R.drawable.ds_ic_action_success),
                    contentDescription = null,
                    tint = colors.onPrimary,
                    modifier = Modifier
                        .size(dimension.medium)
                        .background(color = color.primary, shape = CircleShape),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDSStepIcon() {
    val state = remember { mutableStateOf(DSStepStateType.Initial) }

    LaunchedEffect(state.value) {
        delay(2000L)
        state.value = when (state.value) {
            DSStepStateType.Initial -> DSStepStateType.Progress
            DSStepStateType.Progress -> DSStepStateType.Done
            DSStepStateType.Done -> DSStepStateType.Initial
        }
    }

    Column(modifier = Modifier.padding(dimension.medium)) {
        DSStepIcon(
            state = state.value,
        )
    }
}