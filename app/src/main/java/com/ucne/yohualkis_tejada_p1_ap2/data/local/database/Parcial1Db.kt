package com.ucne.yohualkis_tejada_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ucne.yohualkis_tejada_p1_ap2.data.local.dao.TareaDao
import com.ucne.yohualkis_tejada_p1_ap2.data.local.entities.TareaEntity

@Database(
    entities = [
        TareaEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class Parcial1Db : RoomDatabase(){
    abstract fun TareaDao(): TareaDao
}