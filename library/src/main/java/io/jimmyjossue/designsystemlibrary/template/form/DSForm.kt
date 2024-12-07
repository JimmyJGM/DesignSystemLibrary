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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.components.button.DSButtonPrimary
import io.jimmyjossue.designsystemlibrary.components.separator.DSSpacer
import io.jimmyjossue.designsystemlibrary.template.form.DSFormUtils.toButtonColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormColors
import io.jimmyjossue.designsystemlibrary.template.form.model.DSFormConfig
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
import io.jimmyjossue.designsystemlibrary.theme.catalog.typography
import io.jimmyjossue.designsystemlibrary.utils.doIfItIs
import io.jimmyjossue.designsystemlibrary.utils.extension.borderTop
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colors.background),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(space = config.separationSpace),
            modifier = Modifier
                .weight(weight = 1f)
                .verticalScroll(state = rememberScrollState())
                .padding(horizontal = config.paddingHorizontal)
                .padding(top = config.paddingVertical)
        ) {
            FormLabel(
                value = title,
                style = typography.title,
                colors = colors,
            )

            sections.forEach { section ->
                Column {
                    FormLabel(
                        value = section.title,
                        style = typography.body,
                        colors = colors.copy(typography = colors.typography.alphaHigh),
                        modifier = mdfPaddingBottom(pd = dimension.small)
                    )
                    FormLabel(
                        value = section.description,
                        style = typography.caption,
                        colors = colors.copy(typography = colors.typography.alphaMedium),
                        modifier = mdfPaddingBottom(pd = dimension.medium)
                    )
                    FormSection(
                        elements = section.elements,
                        colors = colors,
                        space = dimension.medium,
                        contentPadding = config.paddingHorizontal.div(other = 2) + dimension.smalled,
                        onChangeValue = onChangeValue,
                    )
                }
            }
            DSSpacer(size = config.paddingVertical)
        }
        Box(
            modifier = Modifier
                .borderTop(color = colors.surface)
                .background(color = colors.surface)
                .padding(vertical = config.paddingVertical)
                .padding(horizontal = config.paddingHorizontal),
            contentAlignment = Alignment.Center,
            content = {
                DSButtonPrimary(
                    modifier = Modifier.fillMaxWidth(),
                    colors = colors.toButtonColors(),
                    text = submitText,
                    onClick = {
                        onSubmit(
                            sections.getValues()
                        )
                    }
                )
            }
        )
    }
}

@SuppressLint("ModifierFactoryExtensionFunction")
private fun mdfPaddingBottom(pd: Dp) = Modifier.padding(bottom = pd)

private enum class Keys {
    Name, PaternalName, MaternalName, User, Password, LegalAge, Legal, Users
}

@Preview
@Composable
fun PreviewDSForm(
    colors: DSFormColors = DSFormUtils. getColors(),
    onSubmit: () -> Unit = {}
) {
    val nameState = remember { mutableStateOf("") }
    val paternalNameState = remember { mutableStateOf("") }
    val maternalNameState = remember { mutableStateOf("") }
    val userState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val legalAgeState = remember { mutableStateOf(false) }
    val legalState = remember { mutableStateOf(false) }
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

    DSForm(
        colors = colors,
        onSubmit = {
            val values = it.joinToString(
                prefix = "[",
                postfix = "]",
                separator = ", ",
                transform =  { value ->
                    "${value.key}: ${value.value}"
                },
            )
            Log.d(
                "fromOnSubmit",
                "data: $values",
            )
            onSubmit()
        },
        sections = listOf(
            DSFormSection(
                title = "Datos personales",
                description = "Escribe los datos personales requeridos para tu cuenta. Estos datos no seran visibles para otros usuarios",
                elements = listOf(
                    InputText(
                        key = Keys.Name.name,
                        value = nameState.value,
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
            }
        },
    )
}
