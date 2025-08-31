package com.ucne.yohualkis_tejada_p1_ap2.di

import android.content.Context
import androidx.room.Room
import com.ucne.yohualkis_tejada_p1_ap2.data.local.database.Parcial1Db
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule{
    @Provides
    @Singleton
    fun database(@ApplicationContext contexto: Context) =
        Room.databaseBuilder(
                context = contexto,
                klass = Parcial1Db::class.java,
                name = "Parcial1.db"
            ).fallbackToDestructiveMigration(false).build()

    @Provides
    fun provideTareaDao(parcial1Db: Parcial1Db) = parcial1Db.TareaDao()
}