package io.jimmyjossue.designsystemlibrary.utils

inline fun <reified T : Any> Any.asObjectOrNull(): T? {
    return try {
        this as T
    } catch (ex: Exception) {
        null
    }
}

fun Any?.isNotNull(): Boolean { return this != null }
fun Any.asBooleanOrNull() = asObjectOrNull<Boolean>()
fun Any.asStringOrNull() = asObjectOrNull<String>()
fun Any.asIntOrNull() = when {
    this is String -> toIntOrNull()
    else -> asObjectOrNull<Int>()
}

val Any.toBooleanOrNull: Boolean? get() = asBooleanOrNull()
val Any.toIntOrNull: Int? get() = asIntOrNull()
val Any.toStringOrNull: String? get() = asStringOrNull()

inline fun <reified T : Any>Any.doIfItIs(block: (T) -> Unit) {
    val value = asObjectOrNull<T>()
    if (value != null) {
        block(value)
    }
}
