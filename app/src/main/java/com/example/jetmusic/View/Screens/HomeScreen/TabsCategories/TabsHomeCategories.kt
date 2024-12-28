package com.example.jetmusic.View.Screens.HomeScreen.TabsCategories

enum class TabsHomeCategories(val title: String, val genreId: String? = null) {
    FOR_YOU("For you"),
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

        fun fromGenreId(genreId: String): TabsHomeCategories? {
            return entries.find { it.genreId == genreId }
        }
    }
}