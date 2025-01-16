package io.jimmyjossue.designsystemlibrary.template.screen.model

import io.jimmyjossue.designsystemlibrary.template.screen.DSScreenActions

interface DSScreenScope {
    fun getColors(): DSScreenColors
    fun getActions(): DSScreenActions
}
