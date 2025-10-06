package uvg.gonzaroky.composables.DataBase

import uvg.gonzaroky.composables.DataBase.Location

class LocationDb {

    private val locations = listOf(
        Location(1, "Earth (C-137)", "Planet", "Dimension C-137"),
        Location(2, "Abadango", "Cluster", "Unknown Dimension"),
        Location(3, "Citadel of Ricks", "Space station", "Unknown Dimension"),
        Location(4, "Worldender's lair", "Planet", "Unknown Dimension"),
        Location(5, "Anatomy Park", "Microverse", "Dimension C-137")
    )

    fun getAllLocations(): List<Location> = locations

    fun getLocationById(id: Int): Location =
        locations.first { it.id == id }
}
