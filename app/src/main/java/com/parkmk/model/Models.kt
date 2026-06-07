package com.parkmk.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class ParkingZone(
    val id: String = "",
    val name: String = "",
    val letter: String = "A",
    val pricePerHour: Int = 50,
    val colorHex: String = "#E94560",
    val workingHours: String = "07:00-22:00"
) {
    val pricePerMinute: Double get() = pricePerHour / 60.0
}

data class ParkingSpot(
    @DocumentId val id: String = "",
    val name: String = "",
    val address: String = "",
    val zoneId: String = "",
    val zoneName: String = "",
    val zoneLetter: String = "A",
    val pricePerHour: Int = 50,
    val totalSpots: Int = 0,
    val openFrom: String = "07:00",
    val openUntil: String = "22:00",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val isActive: Boolean = true
) {
    val pricePerMinute: Double get() = pricePerHour / 60.0
}

data class City(
    @DocumentId val id: String = "",
    val name: String = "",
    val region: String = "",
    val smsNumber: String = "144414",
    val isActive: Boolean = true,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

enum class VehicleType(val label: String) {
    CAR("Патничко"), MOTO("Мотор"), TRUCK("Товарно")
}

data class Vehicle(
    @DocumentId val id: String = "",
    val plate: String = "",
    val plateSms: String = "",
    val displayName: String = "",
    val type: String = VehicleType.CAR.name,
    val colorHex: String = "#B0B0B0",
    val isActive: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null
) {
    companion object {
        fun plateToSms(plate: String) =
            plate.replace("-", "").replace(" ", "").uppercase()
    }
}

enum class SessionStatus { ACTIVE, COMPLETED, CANCELLED }

data class ParkingSession(
    @DocumentId val id: String = "",
    val cityId: String = "",
    val cityName: String = "",
    val spotId: String = "",
    val spotName: String = "",
    val zoneName: String = "",
    val zoneLetter: String = "",
    val vehicleId: String = "",
    val plate: String = "",
    val durationSeconds: Long = 0,
    val pricePerHour: Int = 0,
    val totalCost: Double = 0.0,
    val smsNumber: String = "144414",
    val smsBody: String = "",
    val status: String = SessionStatus.ACTIVE.name,
    @ServerTimestamp val startTime: Timestamp? = null,
    val endTime: Timestamp? = null
)

data class AppUser(
    val uid: String = "",
    val displayName: String = "",
    val email: String = "",
    val phone: String = "",
    val activeCityId: String = "bitola",
    val activeVehicleId: String = "",
    val totalParkings: Long = 0,
    val totalSpentDen: Double = 0.0,
    val fcmToken: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null
)

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    object Empty : UiState<Nothing>()
}

object SampleData {
    val bitolaZones = listOf(
        ParkingZone("zone_a", "Зона А", "A", 50, "#E94560", "07:00-22:00"),
        ParkingZone("zone_b", "Зона Б", "B", 35, "#185FA5", "07:00-22:00"),
        ParkingZone("zone_v", "Зона В", "V", 20, "#2D9E6B", "07:00-20:00")
    )
    val bitolaSpots = listOf(
        ParkingSpot("s1", "Спортска сала",       "Sportski kompleks kaj Shetalishte", "zone_b1", "Зона Б1", "B", 20, 150, "07:00", "22:00", 41.022741, 21.336727),
        ParkingSpot("s2", "Аеро",                "Shirok Sokak",                      "zone_b6", "Зона Б6", "B", 30,  35, "07:00", "22:00", 41.024736, 21.337873),
        ParkingSpot("s3", "Камен Мост",          "Солунска",                          "zone_a6", "Зона А6", "A", 50,  30, "07:00", "22:00", 41.025810, 21.337332),
        ParkingSpot("s4", "Паркинг Епинал",      "Пеце Матичевски",                   "zone_a7", "Зона А7", "A", 50,  75, "07:00", "22:00", 41.028067, 21.335771),
        ParkingSpot("s5", "Бела Кука",           "Димитар Илиевски - Мурато, Битола", "zone_a7", "Зона А",  "A", 50,  30, "07:00", "22:00", 41.028190, 21.337307),
        ParkingSpot("s6", "Пелагонка 2",         "Пеце Матичевски",                   "zone_a10","Зона А10","A", 50,  60, "07:00", "22:00", 41.026999, 21.333048),
        ParkingSpot("s7", "Саат Кула",           "Јорго Османо",                      "zone_a4", "Зона А4", "A", 50,  70, "07:00", "22:00", 41.030624, 21.334645),
        ParkingSpot("s8", "Суд",                 "Булевар 1-ви Мај 69, Битола",       "zone_a2", "Зона А2", "A", 50,  60, "07:00", "22:00", 41.031707, 21.332405),
        ParkingSpot("s9", "Стаклена/Alta Banka", "Јосиф Јосифовски 1, Битола 7000",  "zone_a5", "Зона А5", "A", 50,  80, "07:00", "22:00", 41.030527, 21.336253),
        ParkingSpot("s10", "Јавор Паркинг", "Браќа Мингови 21, Битола 7000", "zone_a1", "Зона А1", "A", 50, 40, "07:00", "22:00", 41.032791, 21.336706),
    )
    val vehicles = listOf(
        Vehicle("v1", "ВТ-1234-АА", "ВТ1234АА", "VW Golf 7",  VehicleType.CAR.name, "#B0B0B0", true),
        Vehicle("v2", "ВТ-5678-БВ", "ВТ5678БВ", "Opel Astra", VehicleType.CAR.name, "#FFFFFF", false)
    )
}
