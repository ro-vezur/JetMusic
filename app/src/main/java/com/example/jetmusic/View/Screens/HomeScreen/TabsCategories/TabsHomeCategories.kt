package com.example.jetmusic.View.Screens.HomeScreen.TabsCategories

enum class TabsHomeCategories(val title: String, val genreId: String? = null) {
    RELAX("Relax", "ambient"),
    WORKOUT("Workout", "dance"),
    TRAVEL("Travel", "world"),
    STUDY("Study", "classical"),
    PARTY("Party", "electronic"),
    SLEEP("Sleep", "lounge"),
    CHILL("Chill", "pop"),
    EXERCISE("Exercise", "hiphop"),
    ROMANCE("Romance", "latin");

    companion object {
        fun indexFromGenreId(genreId: String): Int {
            val genre = entries.find { it.genreId == genreId }?: TabsHomeCategories.entries.first()
            return TabsHomeCategories.entries.indexOf(genre)
        }
    }
}