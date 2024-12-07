package io.jimmyjossue.designsystemlibrary.template.form.model

data class DSFormValue(
    val key: String,
    val value: Any?,
)

fun List<DSFormValue>.getValueByKey(key: String) = firstOrNull { it.key == key }?.value
