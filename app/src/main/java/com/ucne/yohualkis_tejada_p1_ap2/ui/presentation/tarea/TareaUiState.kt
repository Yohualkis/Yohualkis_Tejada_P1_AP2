package com.ucne.yohualkis_tejada_p1_ap2.ui.presentation.tarea

import com.ucne.yohualkis_tejada_p1_ap2.data.local.entities.TareaEntity

data class TareaUiState(
    val tareaId: Int? = null,
    val descripcion: String? = "",
    val tiempo: Int? = null,
    val errorMessageDescripcion: String? = "",
    val errorMessageTiempo: String? = "",
    val guardado: Boolean = false,
    val listaTareas: List<TareaEntity> = emptyList()
)
