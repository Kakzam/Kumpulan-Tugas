package com.kost.`in`.utils

object HitungJarak {

    fun getDistance(currentLatitude: Double,
                    currentLongitude: Double,
                    destinationLatitude: Double,
                    destinationLongitude: Double): Double {
        val earthRadius = 6371
        val dLatitude = Math.toRadians(destinationLatitude - currentLatitude)
        val dLongitude = Math.toRadians(destinationLongitude - currentLongitude)
        val a = Math.sin(dLatitude / 2) * Math.sin(dLatitude / 2) +
                Math.cos(Math.toRadians(currentLatitude)) *
                Math.cos(Math.toRadians(destinationLatitude)) *
                Math.sin(dLongitude / 2) * Math.sin(dLongitude / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return earthRadius * c
    }

}