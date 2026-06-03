package com.parkmk.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.parkmk.data.local.dao.*
import com.parkmk.data.local.entity.*

@Database(
    entities = [VehicleEntity::class, SessionEntity::class, SpotEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ParkMKDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun sessionDao(): SessionDao
    abstract fun spotDao(): SpotDao

    companion object {
        @Volatile private var INSTANCE: ParkMKDatabase? = null

        fun getInstance(context: Context): ParkMKDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ParkMKDatabase::class.java,
                    "parkmk.db"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}
