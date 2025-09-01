package com.ucne.yohualkis_tejada_p1_ap2.ui.presentation.tarea

import com.ucne.yohualkis_tejada_p1_ap2.data.local.entities.TareaEntity

sealed interface TareaEvent {
    data class TareaChange(val tareaId: Int?): TareaEvent
    data class DescripcionChange(val descripcion: String): TareaEvent
    data class TiempoChange(val tiempo: Int?): TareaEvent
    data object GoBackAfterSave: TareaEvent

    data class Delete(val tarea: TareaEntity): TareaEvent
    data object Save: TareaEvent
    data object LimpiarTodo: TareaEvent
}