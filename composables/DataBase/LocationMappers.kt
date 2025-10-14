package com.example.rickmortyapp.composables.DataBase

import com.example.rickmortyapp.composables.DataBase.room.LocationEntity

fun LocationEntity.toLocation(): Location {
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}

fun Location.toEntity(): LocationEntity {
    return LocationEntity(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}