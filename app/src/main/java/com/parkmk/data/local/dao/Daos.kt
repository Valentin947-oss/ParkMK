package com.parkmk.data.local.dao

import androidx.room.*
import com.parkmk.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles ORDER BY isActive DESC, createdAt ASC")
    fun observeAll(): Flow<List<VehicleEntity>>

    @Query("SELECT * FROM vehicles ORDER BY isActive DESC, createdAt ASC")
    suspend fun getAll(): List<VehicleEntity>

    @Query("SELECT * FROM vehicles WHERE isActive = 1 LIMIT 1")
    suspend fun getActive(): VehicleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: VehicleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vehicles: List<VehicleEntity>)

    @Query("UPDATE vehicles SET isActive = 0")
    suspend fun deactivateAll()

    @Query("UPDATE vehicles SET isActive = 1 WHERE id = :id")
    suspend fun setActive(id: String)

    @Query("DELETE FROM vehicles WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM vehicles")
    suspend fun deleteAll()
}

@Dao
interface SessionDao {
    @Query("SELECT * FROM sessions ORDER BY startTime DESC")
    fun observeAll(): Flow<List<SessionEntity>>

    @Query("SELECT * FROM sessions ORDER BY startTime DESC LIMIT 50")
    suspend fun getRecent(): List<SessionEntity>

    @Query("SELECT * FROM sessions WHERE status = 'ACTIVE' LIMIT 1")
    fun observeActive(): Flow<SessionEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: SessionEntity)

    @Query("UPDATE sessions SET status = :status, endTime = :endTime WHERE id = :id")
    suspend fun updateStatus(id: String, status: String, endTime: Long?)

    @Query("DELETE FROM sessions")
    suspend fun deleteAll()
}

@Dao
interface SpotDao {
    @Query("SELECT * FROM spots WHERE isActive = 1")
    suspend fun getAll(): List<SpotEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(spots: List<SpotEntity>)

    @Query("DELETE FROM spots")
    suspend fun deleteAll()
}
