package io.jimmyjossue.designsystemlibrary.components.picker

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import io.jimmyjossue.designsystemlibrary.components.picker.DSPickerFileUtils.toPickerFileItem
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFileAction
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFileColors
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFileConfig
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFileItem
import io.jimmyjossue.designsystemlibrary.components.picker.model.DSPickerFilesSortType
import io.jimmyjossue.designsystemlibrary.components.separator.DSDividerHorizontal
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import io.jimmyjossue.designsystemlibrary.utils.DSFileType
import kotlin.random.Random

@Composable
fun DSPickerFile(
    type: DSFileType,
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    containerShape: Shape = shape.smalled,
    colors: DSPickerFileColors = DSPickerFileUtils.getColors(),
    config: DSPickerFileConfig = DSPickerFileUtils.getConfig(),
    onChangeValues: ((DSPickerFileAction, List<DSPickerFileItem>) -> Unit)? = null,
) {
    val context = LocalContext.current
    val files = remember { mutableStateOf<List<DSPickerFileItem>>(emptyList()) }
    val fileSortType = remember { mutableStateOf(DSPickerFilesSortType.Ascending) }
    val pickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { data ->
            val newItems = data.toPickerFileItem(context = context)
            files.value = files.value.plus(elements = newItems).toSet().toList()
            onChangeValues?.invoke(
                /* action */ DSPickerFileAction.ADDED,
                /* items */ files.value.toList(),
            )
        }
    )

    fun doOnchangeValues(action: DSPickerFileAction) {
        onChangeValues?.invoke(action, files.value.toList())
    }

    fun doOnDeleteUri(uri: Uri) {
        files.value = files.value.filter { it.uri != uri }
        doOnchangeValues(DSPickerFileAction.DELETED)
    }

    fun doOnLaunchPicker() {
        pickerLauncher.launch(
            when (type) {
                is DSFileType.Doc -> arrayOf(
                    "application/msword",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                    "application/pdf",
                    "application/pdf",
                    "text/plain"
                )
                is DSFileType.Spreadsheet -> arrayOf(
                    "application/vnd.ms-excel",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    "application/vnd.oasis.opendocument.spreadsheet"
                )
                is DSFileType.Image -> arrayOf(
                    "image/jpeg",
                    "image/png",
                    "image/gif",
                    "image/heif",
                    "image/heic",
                    "image/bmp",
                    "image/webp"
                )
                is DSFileType.Sound -> arrayOf(
                    "audio/mpeg",
                    "audio/wav",
                    "audio/ogg",
                    "audio/aac",
                    "audio/x-wav"
                )
                is DSFileType.Video -> arrayOf(
                    "video/mp4",
                    "video/x-matroska",
                    "video/x-msvideo",
                    "video/quicktime"

                )
                is DSFileType.Code -> arrayOf(
                    "text/x-java-source",
                    "text/x-c++src",
                    "text/x-python",
                    "application/javascript",
                    "text/html",
                    "application/json"
                )
                is DSFileType.Compressed -> arrayOf(
                    "application/zip",
                    "application/x-rar-compressed",
                    "application/gzip",
                    "application/x-7z-compressed"
                )
                is DSFileType.All -> arrayOf(
                    "*/*"
                )
                is DSFileType.Custom -> type.types.toTypedArray()
            }
        )
    }

    LaunchedEffect(fileSortType.value) {
        Log.d("LaunchedEffect", "change_fileSortType")
        files.value = files.value.reversed()
    }

    PickerFileContent(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        files = files.value,
        containerShape = containerShape,
        filesSortType = fileSortType.value,
        colors = colors,
        config = config,
        onAddClick = ::doOnLaunchPicker,
        onDeleteClick = ::doOnDeleteUri,
        onDeleteAllClick = {
            files.value = emptyList()
            doOnchangeValues(DSPickerFileAction.DELETED)
        },
        onFilesSortClick = {
            fileSortType.value = when (fileSortType.value) {
                DSPickerFilesSortType.Ascending -> DSPickerFilesSortType.Descending
                DSPickerFilesSortType.Descending -> DSPickerFilesSortType.Ascending
            }
        },
    )
}

@Composable
private fun PickerFileContent(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    files: List<DSPickerFileItem> = emptyList(),
    containerShape: Shape = shape.smalled,
    filesSortType: DSPickerFilesSortType = DSPickerFilesSortType.Ascending,
    colors: DSPickerFileColors = DSPickerFileUtils.getColors(),
    config: DSPickerFileConfig = DSPickerFileUtils.getConfig(),
    onAddClick: () -> Unit,
    onDeleteClick: (Uri) -> Unit,
    onDeleteAllClick: () -> Unit,
    onFilesSortClick: () -> Unit,
) {
    val thickness = 1.dp

    @Composable
    fun getListHeight(): Dp {
        val itemPadding = config.contentPaddingItem * 2
        val itemOnlySize = dimension.large - dimension.small
        val itemFullSize = itemPadding + itemOnlySize + thickness
        return itemFullSize * files.size
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = colors.background, shape = containerShape)
            .padding(all = config.contentPaddingParent)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(config.separationElements),
    ) {
        DSPickerFileHeader(
            title = title,
            subtitle = subtitle,
            config = config,
            colors = colors,
            filesSortState = filesSortType,
            isEnabledButtonAdd = files.size < config.maxFiles,
            isEnabledButtonDelete = files.size > 1,
            isNotEmptyList = files.isNotEmpty(),
            onAddItem = onAddClick,
            onSortItems = onFilesSortClick,
            onDeleteAllItems = onDeleteAllClick
        )
        if (files.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.height(height = getListHeight()),
            ) {
                items(
                    items = files.toList(),
                    key = { it.uri }
                ) { file ->
                    DSPickerFileItemWidget(
                        contentPadding = config.contentPaddingItem,
                        shapeItem = shape.smalled,
                        onDelete = onDeleteClick,
                        colors = colors,
                        data = file,
                    )
                    if (file.uri != files.last().uri) {
                        DSDividerHorizontal(
                            modifier = Modifier.padding(horizontal = config.contentPaddingItem),
                            lineColor = colors.surface,
                            thickness = thickness,
                        )
                    }
                }
            }
        } else {
            DSPickerFileUniqueButtonAdd(
                text = config.addButtonText,
                onClick = onAddClick,
                colors = colors,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewPickerFileContent() {
    val file = DSPickerFileItem("Nombre del archivo.png", 36271138L, "".toUri())
    val files = emptyList<DSPickerFileItem>()
    val filesState = remember { mutableStateOf(files) }
    val delete = { uri: Uri -> filesState.value = filesState.value.filter { it.uri != uri } }
    val deleteAll = { filesState.value = emptyList() }
    val sort = { filesState.value = filesState.value.reversed() }
    val add = {
        val new = file.copy(uri = Random.nextLong().toString().toUri())
        filesState.value = filesState.value.plus(new)
    }

    PickerFileContent(
        modifier = Modifier.padding(dimension.small),
        title = "Selecciona tus archivos",
        subtitle = "los archivos que selecciones no pueden exeder los 35 MB de tamaño cada uno",
        files = filesState.value,
        containerShape = shape.medium,
        config = DSPickerFileUtils.getConfig(
            addButtonText = "Agregar archivo",
            deleteButtonText = "Todos",
            contentPaddingParent = dimension.semiLarge,
            contentPaddingItem = dimension.small,
            separationElements = dimension.medium,
            maxFiles = 7,
            sizeOfEachFileInBytes = 987918947L,
        ),
        onAddClick = add,
        onDeleteAllClick = deleteAll,
        onDeleteClick = delete,
        onFilesSortClick = sort,
    )
}
