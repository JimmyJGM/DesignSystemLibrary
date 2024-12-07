package io.jimmyjossue.designsystemlibrary.utils.textdecorator

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.withStyle
import io.jimmyjossue.designsystemlibrary.theme.catalog.LocalColors

private const val DECORATES = "<(/?)(b|sb|i|t|primary|accent|typography|success|warning|error)>"

private fun String.parseDecoratedText(): List<DSDecoratorTextValue> {
    val regex = Regex(DECORATES)
    val matches = regex.findAll(input = this)

    val result = mutableListOf<DSDecoratorTextValue>()
    val stack = mutableListOf<DSDecoratorTextType>()
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
            "<b>" -> stack.add(DSDecoratorTextType.BOLD)
            "</b>" -> stack.remove(DSDecoratorTextType.BOLD)
            "<sb>" -> stack.add(DSDecoratorTextType.SEMI_BOLD)
            "</sb>" -> stack.remove(DSDecoratorTextType.SEMI_BOLD)
            "<i>" -> stack.add(DSDecoratorTextType.ITALIC)
            "</i>" -> stack.remove(DSDecoratorTextType.ITALIC)
            "<t>" -> stack.add(DSDecoratorTextType.STRIKETHROUGH)
            "</t>" -> stack.remove(DSDecoratorTextType.STRIKETHROUGH)
            "<primary>" -> stack.add(DSDecoratorTextType.PRIMARY)
            "</primary>" -> stack.remove(DSDecoratorTextType.PRIMARY)
            "<accent>" -> stack.add(DSDecoratorTextType.ACCENT)
            "</accent>" -> stack.remove(DSDecoratorTextType.ACCENT)
            "<typography>" -> stack.add(DSDecoratorTextType.TYPOGRAPHY)
            "</typography>" -> stack.remove(DSDecoratorTextType.TYPOGRAPHY)
            "<success>" -> stack.add(DSDecoratorTextType.SUCCESS)
            "</success>" -> stack.remove(DSDecoratorTextType.SUCCESS)
            "<warning>" -> stack.add(DSDecoratorTextType.WARNING)
            "</warning>" -> stack.remove(DSDecoratorTextType.WARNING)
            "<error>" -> stack.add(DSDecoratorTextType.ERROR)
            "</error>" -> stack.remove(DSDecoratorTextType.ERROR)
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

internal fun List<DSDecoratorTextType>.getBold() = ExtraBold.takeIf { contains(DSDecoratorTextType.BOLD) }
    ?: SemiBold.takeIf { contains(DSDecoratorTextType.SEMI_BOLD) }

internal fun List<DSDecoratorTextType>.getItalic() = Italic.takeIf { contains(DSDecoratorTextType.ITALIC) }

@Composable
internal fun List<DSDecoratorTextType>.getColor(): Color {
    val currentColors = LocalColors.current
    return when {
        this.contains(DSDecoratorTextType.PRIMARY) -> currentColors.primary
        this.contains(DSDecoratorTextType.ACCENT) -> currentColors.accent
        this.contains(DSDecoratorTextType.TYPOGRAPHY) -> currentColors.typography
        this.contains(DSDecoratorTextType.SUCCESS) -> currentColors.success
        this.contains(DSDecoratorTextType.WARNING) -> currentColors.warning
        this.contains(DSDecoratorTextType.ERROR) -> currentColors.error
        else -> Color.Unspecified
    }
}

@Composable
fun String.decoratedAnnotatedString() = buildAnnotatedString {
    this@decoratedAnnotatedString.parseDecoratedText().forEach {
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