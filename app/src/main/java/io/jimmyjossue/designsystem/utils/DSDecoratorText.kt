package io.jimmyjossue.designsystem.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.withStyle
import io.jimmyjossue.designsystem.theme.color

private const val DECORATES = "<(/?)(b|sb|i|t|primary|accent|typography|success|warning|error)>"

internal enum class DSDecoratorText {
    BOLD, SEMI_BOLD, ITALIC, STRIKETHROUGH, PRIMARY, ACCENT, TYPOGRAPHY, SUCCESS, WARNING, ERROR
}

internal data class DSDecoratorTextValue(
    val value: String,
    val decorators: List<DSDecoratorText> = emptyList()
)

internal fun String.parseDecoratedText(): List<DSDecoratorTextValue> {
    val regex = Regex(DECORATES)
    val matches = regex.findAll(input = this)

    val result = mutableListOf<DSDecoratorTextValue>()
    val stack = mutableListOf<DSDecoratorText>()
    var lastIndex = 0

    matches.forEach { match ->
        val tag = match.value
        val startIndex = match.range.first
        if (startIndex > lastIndex) {
            result.add(
                element = DSDecoratorTextValue(
                    value = this.substring(lastIndex, startIndex),
                    decorators = stack.toList()
                )
            )
        }
        when (tag) {
            "<b>" -> stack.add(DSDecoratorText.BOLD)
            "</b>" -> stack.remove(DSDecoratorText.BOLD)
            "<sb>" -> stack.add(DSDecoratorText.SEMI_BOLD)
            "</sb>" -> stack.remove(DSDecoratorText.SEMI_BOLD)
            "<i>" -> stack.add(DSDecoratorText.ITALIC)
            "</i>" -> stack.remove(DSDecoratorText.ITALIC)
            "<t>" -> stack.add(DSDecoratorText.STRIKETHROUGH)
            "</t>" -> stack.remove(DSDecoratorText.STRIKETHROUGH)
            "<primary>" -> stack.add(DSDecoratorText.PRIMARY)
            "</primary>" -> stack.remove(DSDecoratorText.PRIMARY)
            "<accent>" -> stack.add(DSDecoratorText.ACCENT)
            "</accent>" -> stack.remove(DSDecoratorText.ACCENT)
            "<typography>" -> stack.add(DSDecoratorText.TYPOGRAPHY)
            "</typography>" -> stack.remove(DSDecoratorText.TYPOGRAPHY)
            "<success>" -> stack.add(DSDecoratorText.SUCCESS)
            "</success>" -> stack.remove(DSDecoratorText.SUCCESS)
            "<warning>" -> stack.add(DSDecoratorText.WARNING)
            "</warning>" -> stack.remove(DSDecoratorText.WARNING)
            "<error>" -> stack.add(DSDecoratorText.ERROR)
            "</error>" -> stack.remove(DSDecoratorText.ERROR)
        }
        lastIndex = match.range.last + 1
    }

    if (lastIndex < this.length) {
        result.add(
            DSDecoratorTextValue(
                value = this.substring(lastIndex).replace(DECORATES.toRegex(), ""),
                decorators = stack.toList()
            )
        )
    }

    return result
}

internal fun List<DSDecoratorText>.getBold() = ExtraBold.takeIf { contains(DSDecoratorText.BOLD) }
    ?: SemiBold.takeIf { contains(DSDecoratorText.SEMI_BOLD) }

internal fun List<DSDecoratorText>.getItalic() = Italic.takeIf { contains(DSDecoratorText.ITALIC) }

@Composable
internal fun List<DSDecoratorText>.getColor() = when {
    this.contains(DSDecoratorText.PRIMARY) -> color.primary
    this.contains(DSDecoratorText.ACCENT) -> color.accent
    this.contains(DSDecoratorText.TYPOGRAPHY) -> color.typography
    this.contains(DSDecoratorText.SUCCESS) -> color.success
    this.contains(DSDecoratorText.WARNING) -> color.warning
    this.contains(DSDecoratorText.ERROR) -> color.error
    else -> Color.Unspecified
}

@Composable
internal fun String.parseDecoratedAnnotatedString() = buildAnnotatedString {
    this@parseDecoratedAnnotatedString.parseDecoratedText().forEach {
        when (it.decorators.isEmpty()) {
            true -> append(it.value)
            false -> withStyle(
                style = SpanStyle(
                    fontWeight = it.decorators.getBold(),
                    fontStyle = it.decorators.getItalic(),
                    color = it.decorators.getColor(),
                ),
                block = { append(it.value) }
            )
        }
    }
}