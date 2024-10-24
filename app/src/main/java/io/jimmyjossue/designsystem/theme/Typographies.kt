package io.jimmyjossue.designsystem.theme

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class DSTypography(
    val header: TextStyle = header(),
    val titleLarge: TextStyle = titleLarge(),
    val title: TextStyle = title(),
    val body: TextStyle = body(),
    val caption: TextStyle = caption(),
)

val localTypography = staticCompositionLocalOf { DSTypography() }

val typography: DSTypography
    @Composable
    @ReadOnlyComposable
    get() = localTypography.current

val TextStyle.bold get() = this.copy(fontWeight = FontWeight.Bold)
val TextStyle.semiBold get() = this.copy(fontWeight = FontWeight.SemiBold)
val TextStyle.medium get() = this.copy(fontWeight = FontWeight.Medium)

@SuppressLint("DiscouragedApi")
@Composable
fun fontResources( font: String): Font {
    val context = LocalContext.current
    val fontRes = context.resources.getIdentifier(font, "font", context.packageName)
    return Font(fontRes)
}


fun header(): TextStyle {
    return TextStyle(
        fontSize = 36.sp,
        lineHeight = 44.4.sp,
        //fontFamily = FontFamily(fontResources("roboto_medium")),
        fontWeight = FontWeight.Normal,
    )
}

fun titleLarge(): TextStyle {
    return TextStyle(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        //fontFamily = FontFamily(fontResources("roboto_medium")),
        fontWeight = FontWeight.Normal,
    )
}

fun title(): TextStyle {
    return TextStyle(
        fontSize = 18.sp,
        lineHeight = 22.sp,
        //fontFamily = FontFamily(fontResources("roboto_medium")),
        fontWeight = FontWeight.Normal,
    )
}

fun body(): TextStyle {
    return TextStyle(
        fontSize = 14.sp,
        lineHeight = 19.6.sp,
        //fontFamily = FontFamily(fontResources("roboto_regular")),
        fontWeight = FontWeight.Normal,
    )
}

fun caption(): TextStyle {
    return TextStyle(
        //fontFamily = FontFamily(fontResources("roboto_regular")),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    )
}
