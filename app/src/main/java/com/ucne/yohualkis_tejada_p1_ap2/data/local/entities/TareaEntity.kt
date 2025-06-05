package com.ucne.yohualkis_tejada_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Tareas")
data class TareaEntity(
    @PrimaryKey
    val tareaId: Int?,
    val descripcion: String,
    val tiempo: Int // en minutos
)
