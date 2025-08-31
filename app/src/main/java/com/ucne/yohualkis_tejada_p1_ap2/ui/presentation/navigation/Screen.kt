package com.ucne.yohualkis_tejada_p1_ap2.ui.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object TareasList: Screen()

    @Serializable
    data class Tarea(val tareaId: Int?): Screen()
}
