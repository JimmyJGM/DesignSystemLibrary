package io.jimmyjossue.designsystemlibrary.components.step.config

sealed class DSStepType {
    data class Number(val value: Int): DSStepType()
    data object Icon: DSStepType()
}