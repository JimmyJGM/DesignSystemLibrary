package io.jimmyjossue.designsystemlibrary.utils.textdecorator

internal data class DSDecoratorTextValue(
    val value: String,
    val decorators: List<DSDecoratorTextType> = emptyList()
)
