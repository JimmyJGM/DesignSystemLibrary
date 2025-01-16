package io.jimmyjossue.designsystemlibrary.components.picker

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns.DISPLAY_NAME
import android.provider.OpenableColumns.SIZE
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.core.net.toFile
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFileColors
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFileConfig
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFileItem
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.utils.DSFileType
import io.jimmyjossue.designsystemlibrary.utils.extension.fileExtension
import java.util.Locale

object DSPickerFileUtils {

    private val imageExtensions = listOf("jpg", "jpeg", "png", "gif", "bmp", "heic", "heif")
    private val soundExtensions = listOf("mp3", "wav", "aac", "flac", "ogg", "opus")
    private val videoExtensions = listOf("mp4", "avi", "mov", "mkv", "flv", "wmv", "mov")
    private val compressedExtensions = listOf("zip", "rar", "tar", "gz", "7z")
    private val documentExtensions = listOf("doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt", "pdf")
    private val wordExtensions = listOf("doc", "docm", "docx", "dot", "dotx", "word")
    private val pdfExtensions = listOf("pdf")
    private val excelExtensions = listOf("xlsx", "xls", "xlsm", "xltx", "xltm", "xlsb", "csv", "tsv")
    private val codeExtensions = listOf("htm", "html", "java", "gradle", "kt", "kts", "cpp", "js", "py", "rb", "cs", "go", "dart", "class", "jar", "dll", "so", "bat", "json", "yaml", "ini", "xml", "properties", "md", "txt", "csv", "log", "db", "tar")

    @Composable
    fun getColors(
        primary: Color = color.primary,
        primaryDisabled: Color = color.primaryDisabled,
        onPrimary: Color = color.onPrimary,
        background: Color = color.background,
        surface: Color = color.surface,
        typography: Color = color.typography,
    ) = DSPickerFileColors(
        primary = primary,
        primaryDisabled = primaryDisabled,
        onPrimary = onPrimary,
        background = background,
        surface = surface,
        typography = typography
    )

    @Composable
    fun getConfig(
        addButtonText: String? = null,
        deleteButtonText: String? = null,
        contentPaddingParent: Dp = dimension.small,
        contentPaddingItem: Dp = dimension.small,
        separationElements: Dp = dimension.small,
        maxFiles: Int = Int.MAX_VALUE,
        sizeOfEachFileInBytes: Long = Long.MAX_VALUE,
    ) = DSPickerFileConfig(
        maxFiles = maxFiles,
        addButtonText = addButtonText,
        deleteButtonText = deleteButtonText,
        contentPaddingParent = contentPaddingParent,
        contentPaddingItem = contentPaddingItem,
        separationElements = separationElements,
        sizeOfEachFileInBytes = sizeOfEachFileInBytes,
    )

    @Composable
    internal fun String.getIcon() = when (this.fileExtension.lowercase()) {
        in imageExtensions -> painterResource(R.drawable.ic_system_file_image)
        in soundExtensions -> painterResource(R.drawable.ic_system_file_sound)
        in videoExtensions -> painterResource(R.drawable.ic_system_file_video)
        in pdfExtensions -> painterResource(R.drawable.ic_system_file_pdf)
        in wordExtensions -> painterResource(R.drawable.ic_system_file_word)
        in excelExtensions -> painterResource(R.drawable.ic_system_file_excel)
        in documentExtensions -> painterResource(R.drawable.ic_system_file_doc)
        in codeExtensions -> painterResource(R.drawable.ic_system_file_code)
        in compressedExtensions -> painterResource(R.drawable.ic_system_file_compressed)
        else -> painterResource(R.drawable.ic_system_file_undefined)
    }

    internal fun String.toDSFileType() = when (this.fileExtension) {
        in imageExtensions -> DSFileType.Image
        in soundExtensions -> DSFileType.Sound
        in videoExtensions -> DSFileType.Video
        in documentExtensions -> DSFileType.Doc
        in codeExtensions -> DSFileType.Code
        in compressedExtensions -> DSFileType.Compressed
        else -> DSFileType.All
    }

    private fun getFileDetails(context: Context, uri: Uri): DSPickerFileItem? {
        return when (uri.scheme) {
            "content" -> context.contentResolver.query(uri, null, null, null, null)?.let { cursor ->
                when (cursor.moveToFirst()) {
                    true -> DSPickerFileItem(
                        name = cursor.getString(cursor.getColumnIndexOrThrow(DISPLAY_NAME)),
                        size = cursor.getLong(cursor.getColumnIndexOrThrow(SIZE)),
                        uri = uri,
                    ).also {
                        cursor.close()
                    }

                    false -> null
                }
            }
            "file" -> {
                val file = uri.toFile()
                DSPickerFileItem(
                    name = file.name,
                    size = file.length(),
                    uri = uri,
                ).also {
                    file.delete()
                }
            }
            else -> null
        }
    }

    internal fun Long.toReadableFormat(locale: Locale? = null): String {
        val kb = 1024
        val mb = kb * 1024
        val gb = mb * 1024

        return when {
            this >= gb -> String.format(locale = locale, format = "%.2f GB", this / gb.toDouble())
            this >= mb -> String.format(locale = locale, format = "%.2f MB", this / mb.toDouble())
            this >= kb -> String.format(locale = locale, format = "%.2f KB", this / kb.toDouble())

            else -> "$this bytes"
        }
    }

    internal fun DSFileType.getExtensions() = when (this) {
        is DSFileType.Image -> imageExtensions
        is DSFileType.Sound -> soundExtensions
        is DSFileType.Video -> videoExtensions
        is DSFileType.Doc -> documentExtensions
        is DSFileType.Code -> codeExtensions
        is DSFileType.Compressed -> compressedExtensions
        is DSFileType.Custom -> this.types
        else -> emptyList()
    }

    internal fun List<Uri>.toPickerFileItem(context: Context) = mapNotNull {
        getFileDetails(context = context, uri = it)
    }.toSet()
}
