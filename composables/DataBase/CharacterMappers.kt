package com.example.rickmortyapp.composables.DataBase

import com.example.rickmortyapp.composables.DataBase.room.CharacterEntity

// Convierte de Entity → Character
fun CharacterEntity.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image
    )
}

// Convierte de Character → Entity
fun Character.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image
    )
}