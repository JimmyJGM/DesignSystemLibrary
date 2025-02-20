package io.jimmyjossue.designsystemlibrary.template.form

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonPrimary
import io.jimmyjossue.designsystemlibrary.components.input.DSInputIcon
import io.jimmyjossue.designsystemlibrary.components.input.config.DSKeyboardType
import io.jimmyjossue.designsystemlibrary.components.picker.DSPickerFile
import io.jimmyjossue.designsystemlibrary.components.picker.DSPickerFileUtils
import io.jimmyjossue.designsystemlibrary.components.selectors.chips.DSChip
import io.jimmyjossue.designsystemlibrary.components.selectors.chips.staticChips
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacer
import io.jimmyjossue.designsystemlibrary.template.form.DSFormUtils.toButtonColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormConfig
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement.InputDropdown
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement.InputText
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement.LabelBody
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement.LabelCaption
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormElement.ToggleSwitch
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormSection
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormValue
import io.jimmyjossue.designsystemlibrary.template.form.model.getValues
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaHigh
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.theme.catalog.shape
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.DSFileType
import io.jimmyjossue.designsystemlibrary.utils.asObjectOrNull
import io.jimmyjossue.designsystemlibrary.utils.doIfItIs
import io.jimmyjossue.designsystemlibrary.utils.iconOptionsDots
import io.jimmyjossue.designsystemlibrary.utils.isNotNull
import io.jimmyjossue.designsystemlibrary.utils.toBooleanOrNull
import io.jimmyjossue.designsystemlibrary.utils.toIntOrNull
import io.jimmyjossue.designsystemlibrary.utils.toStringOrNull

@Composable
fun DSForm(
    modifier: Modifier = Modifier,
    title: String? = null,
    sections: List<DSFormSection>,
    submitText: String = stringResource(id = R.string.ds_onboarding_btn_next),
    colors: DSFormColors = DSFormUtils.getColors(),
    config: DSFormConfig = DSFormUtils.getConfig(),
    onChangeValue: (value: DSFormValue) -> Unit,
    onSubmit: (List<DSFormValue>) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    fun onFocusDown() = focusManager.moveFocus(focusDirection = FocusDirection.Down)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colors.background)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(space = config.spaceSections),
    ) {
        FormLabel(
            value = title,
            colors = colors,
            style = typography.title,
            modifier = Modifier
                .padding(horizontal = config.paddingForm)
                .padding(top = config.paddingForm)
        )
        DSPickerFile(
            modifier = Modifier.padding(horizontal = config.paddingForm),
            type = DSFileType.Image,
            title = "Elige los archivos",
            subtitle = "Los archivos que se eligan no deben de exeder el tamaño de 54 MB por cada uno.",
            containerShape = shape.small,
            colors = DSPickerFileUtils.getColors(
                background = colors.background,
                surface = colors.surface
            ),
            config = DSPickerFileUtils.getConfig(
                maxFiles = 7,
                contentPaddingParent = dimension.none,
                contentPaddingItem = config.paddingElements,
                addButtonText = "Agregar archivos",
                deleteButtonText = "Borrar todos",
                separationElements = dimension.small
            ),
        )

        sections.forEachIndexed { index, section ->
            Column(
                modifier = Modifier.padding(
                    top = when (index == 0) {
                        true -> config.spaceSections.div(other = 2)
                        false -> dimension.none
                    }
                )
            ) {
                FormLabel(
                    value = section.title,
                    style = typography.body,
                    colors = colors.copy(typography = colors.typography.alphaHigh),
                    modifier = mdfPadding(
                        bottom = dimension.small,
                        horizontal = config.paddingForm
                    )
                )
                FormLabel(
                    value = section.description,
                    style = typography.caption,
                    colors = colors.copy(typography = colors.typography.alphaMedium),
                    modifier = mdfPadding(
                        bottom = dimension.small,
                        horizontal = config.paddingForm
                    )
                )
                Box(
                    modifier = mdfPadding(
                        bottom = dimension.small,
                        horizontal = config.paddingForm
                    )
                ) {
                    FormSectionElement(
                        elements = section.elements,
                        colors = colors,
                        space = dimension.medium,
                        withBackground = section.withBackground,
                        contentPadding = config.paddingForm.div(other = 2) + dimension.smalled,
                        onChangeValue = onChangeValue,
                        onFocusDown = ::onFocusDown,
                    )
                }
            }
        }

        DSSpacer(size = config.spaceSections)

        DSButtonPrimary(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = config.paddingForm)
                .padding(horizontal = config.paddingForm),
            colors = colors.toButtonColors(),
            text = submitText,
            onClick = {
                onSubmit(sections.getValues())
            }
        )
    }
}

@SuppressLint("ModifierFactoryExtensionFunction")
private fun mdfPadding(bottom: Dp, horizontal: Dp) = Modifier
    .padding(bottom = bottom)
    .padding(horizontal = horizontal)

private enum class Keys {
    Name, PaternalName, MaternalName, User, Password, LegalAge, Legal, Users, options, UserImage
}

@Preview
@Composable
fun PreviewDSForm(
    colors: DSFormColors = DSFormUtils.getColors(),
    onSubmit: () -> Unit = {}
) {
    val nameState = remember { mutableStateOf("") }
    val paternalNameState = remember { mutableStateOf("") }
    val maternalNameState = remember { mutableStateOf("") }
    val userState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val legalAgeState = remember { mutableStateOf(false) }
    val legalState = remember { mutableStateOf(false) }
    val chips = remember { mutableStateOf(staticChips) }
    val optionSelectedState = remember { mutableStateOf<String?>(null) }
    val options = listOf(
        "Luz Belen",
        "jimmy Jossue",
        "Josmar Julian",
        "Joseph Joel",
        "Israel Salazar",
    )

    val onChangeText = { key: String, value: String ->
        when (key) {
            Keys.Name.name -> nameState.value = value
            Keys.PaternalName.name -> paternalNameState.value = value
            Keys.MaternalName.name -> maternalNameState.value = value
            Keys.User.name -> userState.value = value
            Keys.Password.name -> passwordState.value = value
            Keys.Users.name -> optionSelectedState.value = value
        }
    }

    val onChangeBoolean = { key: String, value: Boolean ->
        when (key) {
            Keys.LegalAge.name -> legalAgeState.value = value
            Keys.Legal.name -> legalState.value = value
        }
    }

    val onChangeAny = { key: String, value: Any ->
        when (key) {
            Keys.options.name -> value.asObjectOrNull<DSChip>()?.let { chip ->
                val newOptions = chips.value.toMutableList()
                val finned = chips.value.find { it.id == chip.id }
                val index = chips.value.indexOf(finned)
                if (index != -1) {
                    newOptions[index] = chip
                    chips.value = newOptions
                }
            }
            else -> Unit
        }
    }

    DSForm(
        colors = colors,
        onSubmit = {
            val values = it.joinToString(
                prefix = "[",
                postfix = "]",
                separator = ", ",
                transform = { value ->
                    "${value.key}: ${value.value}"
                },
            )
            Log.d("fromOnSubmit", "data: $values",)
            onSubmit()
        },
        sections = listOf(
            DSFormSection(
                title = "Datos personales",
                description = "Escribe los datos personales requeridos para tu cuenta. Estos datos no seran visibles para otros usuarios",
                withBackground = false,
                elements = listOf(
                    DSFormElement.PickerImage(
                        displayName = "Imagen de perfil",
                        key = Keys.UserImage.name,
                        label = "Elige una imagen de perfil",
                        helper = "Solo puedes elegir 3 opciones como máximo.",
                        changeText = "Cambiar imagen",
                        upLoadText = "Agregar imagen",
                        supportedExtensionsLabelText = "Formato de imagen soportado"
                    ),
                    DSFormElement.InputChips(
                        options = chips.value,
                        displayName = "Nombre",
                        key = Keys.options.name,
                        maxSelected = 3,
                        label = "opciones",
                        helper = "Solo puedes elegir 3 opciones como máximo.",
                    ),
                    InputText(
                        key = Keys.Name.name,
                        value = nameState.value,
                        keyboardType = DSKeyboardType.Uri,
                        trailingIcon = DSInputIcon(iconOptionsDots),
                        displayName = "Nombre",
                        label = "Nombre",
                        hint = "Escribe tu nombre"
                    ),
                    InputText(
                        key = Keys.PaternalName.name,
                        value = paternalNameState.value,
                        displayName = "Apellido paterno",
                        label = "Apellido paterno",
                        hint = "Escribe tu ppellido paterno"
                    ),
                    InputText(
                        key = Keys.MaternalName.name,
                        value = maternalNameState.value,
                        displayName = "Apellido materno",
                        label = "Apellido materno",
                        hint = "Escribe tu apellido materno",
                        helper = "Puedes omitir este dato"
                    ),
                    ToggleSwitch(
                        key = Keys.LegalAge.name,
                        label = "¿Soy mayor de edad?",
                        isSelected = legalAgeState.value,
                        displayName = "Edad legal",
                        helper = "En caso de aceptar ser mayor de edad, el uso de esta app es bajo tu <accent><b>responsabilidad</b></accent>"
                    )
                )
            ),
            DSFormSection(
                title = "Datos de la cuenta",
                withBackground = false,
                elements = listOf(
                    InputText(
                        key = Keys.User.name,
                        value = userState.value,
                        displayName = "Nombre de usuario",
                        label = "Nombre de usuario",
                        hint = "Escribe tu nombre de usuario",
                    ),
                    InputText(
                        key = Keys.Password.name,
                        value = passwordState.value,
                        displayName = "Contraseña",
                        label = "Contraseña",
                        hint = "Escribe tu contraseña",
                        helper = "La contraseña debe de se de al menos 8 caracteres combinados entre minusculas y mayusculase "
                    ),
                    InputDropdown(
                        key = Keys.Users.name,
                        value = optionSelectedState.value,
                        options = options,
                        displayName = "Usuarios",
                        hint = "Selecciona una opcion",
                        helper = "Puedes elegir un usuario de la lista de usuarios."
                    ),
                )
            ),
            DSFormSection(
                title = "Legales",
                withBackground = true,
                elements = listOf(
                    ToggleSwitch(
                        key = Keys.Legal.name,
                        label = "Estoy de acuerdo con el aviso de privacidad",
                        isSelected = legalState.value,
                        isEnabled = legalAgeState.value,
                        displayName = "legal",
                    ),
                    LabelBody(
                        isVisible = legalState.value,
                        value = "Aviso de privacidad"
                    ),
                    LabelCaption(
                        isVisible = legalState.value,
                        value = "Por este medio acepto los <primary>terminos</primary> y <primary>condiciones</primary> de la applicacion"
                    ),
                )
            ),
        ),
        onChangeValue = { data ->
            Log.d("fromOnChangeValue", "value: ${data.value}")
            when {
                data.value?.toStringOrNull.isNotNull() -> data.value?.doIfItIs<String> {
                    Log.d("fromOnChangeValue_Text", "${data.key}: $it")
                    onChangeText(data.key, it)
                }
                data.value?.toBooleanOrNull.isNotNull() -> data.value?.doIfItIs<Boolean> {
                    Log.d("fromOnChangeValue_Boolean", "${data.key}: $it")
                    onChangeBoolean(data.key, it)
                }
                data.value?.toIntOrNull.isNotNull() -> data.value?.doIfItIs<Int> {
                    Log.d("fromOnChangeValue_Int", "${data.key}: $it")
                }
                else -> data.value?.doIfItIs<Any> {
                    Log.d("fromOnChangeValue_Any", "${data.key}: $it")
                    onChangeAny(data.key, it)
                }
            }
        },
    )
}
