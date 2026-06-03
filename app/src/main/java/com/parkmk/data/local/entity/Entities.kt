package com.parkmk.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey val id: String,
    val plate: String,
    val plateSms: String,
    val displayName: String,
    val type: String,
    val colorHex: String,
    val isActive: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey val id: String,
    val cityId: String,
    val cityName: String,
    val spotId: String,
    val spotName: String,
    val zoneName: String,
    val zoneLetter: String,
    val vehicleId: String,
    val plate: String,
    val durationSeconds: Long,
    val pricePerHour: Int,
    val totalCost: Double,
    val smsNumber: String,
    val smsBody: String,
    val status: String,
    val startTime: Long,
    val endTime: Long? = null
)

@Entity(tableName = "spots")
data class SpotEntity(
    @PrimaryKey val id: String,
    val name: String,
    val address: String,
    val zoneId: String,
    val zoneName: String,
    val zoneLetter: String,
    val pricePerHour: Int,
    val totalSpots: Int,
    val openFrom: String,
    val openUntil: String,
    val latitude: Double,
    val longitude: Double,
    val isActive: Boolean,
    val cachedAt: Long = System.currentTimeMillis()
)
