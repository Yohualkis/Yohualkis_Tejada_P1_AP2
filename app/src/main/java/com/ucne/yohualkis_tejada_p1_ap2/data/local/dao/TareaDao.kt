package com.ucne.yohualkis_tejada_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.ucne.yohualkis_tejada_p1_ap2.data.local.entities.TareaEntity
import kotlinx.coroutines.flow.Flow

@Dao
sealed interface TareaDao {
    @Upsert
    suspend fun save(tarea: TareaEntity)

    @Query("select * from Tareas where tareaId = :id limit 1")
    suspend fun find(id: Int?): TareaEntity

    @Delete
    suspend fun delete(tarea: TareaEntity)

    @Query("select * from Tareas")
    fun getAll(): Flow<List<TareaEntity>>
}