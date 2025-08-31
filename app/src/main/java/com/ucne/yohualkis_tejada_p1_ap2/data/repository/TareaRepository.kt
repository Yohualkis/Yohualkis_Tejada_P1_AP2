package com.ucne.yohualkis_tejada_p1_ap2.data.repository

import com.ucne.yohualkis_tejada_p1_ap2.data.local.dao.TareaDao
import com.ucne.yohualkis_tejada_p1_ap2.data.local.entities.TareaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TareaRepository @Inject constructor(
    private val dao: TareaDao
) {
    suspend fun save(tarea: TareaEntity) = dao.save(tarea)
    suspend fun find(id: Int?): TareaEntity = dao.find(id)
    suspend fun delete(tarea: TareaEntity) = dao.delete(tarea)
    fun getAll(): Flow<List<TareaEntity>> = dao.getAll()
}