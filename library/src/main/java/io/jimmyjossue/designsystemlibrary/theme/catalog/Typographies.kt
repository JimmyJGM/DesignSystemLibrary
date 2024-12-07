package io.jimmyjossue.designsystemlibrary.theme.catalog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class DSTypography(
    val header: TextStyle = header(),
    val titleLarge: TextStyle = titleLarge(),
    val title: TextStyle = title(),
    val body: TextStyle = body(),
    val caption: TextStyle = caption(),
    val button: TextStyle = button()
)

fun header() = TextStyle(fontSize = 36.sp, lineHeight = 44.4.sp, fontWeight = FontWeight.Normal)
fun titleLarge() = TextStyle(fontSize = 24.sp, lineHeight = 32.sp, fontWeight = FontWeight.Normal)
fun title() =  TextStyle(fontSize = 18.sp, lineHeight = 22.sp, fontWeight = FontWeight.Normal)
fun button() = TextStyle(fontSize = 16.sp, lineHeight = 20.sp, fontWeight = FontWeight.Normal)
fun body() = TextStyle(fontSize = 14.sp, lineHeight = 19.6.sp, fontWeight = FontWeight.Normal)
fun caption() = TextStyle(fontWeight = FontWeight.Normal, fontSize = 12.sp)

val TextStyle.bold get() = this.copy(fontWeight = FontWeight.Bold)
val TextStyle.semiBold get() = this.copy(fontWeight = FontWeight.SemiBold)
val TextStyle.medium get() = this.copy(fontWeight = FontWeight.Medium)
val TextStyle.normal get() = this.copy(fontWeight = FontWeight.Normal)

val localTypography = staticCompositionLocalOf { DSTypography() }

val typography: DSTypography
    @Composable
    @ReadOnlyComposable
    get() = localTypography.current

