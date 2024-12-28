package com.example.jetmusic.data.DTOs.Genres

enum class MusicGenres(val tag: String, val displayName: String, val image: String) {
    ROCK("rock", "Rock", rockImage),
    POP("pop", "Pop", popImage),
    JAZZ("jazz", "Jazz", jazzImage),
    CLASSICAL("classical", "Classical", classicalImage),
    HIPHOP("hiphop", "Hip-Hop", hiphopImage),
    ELECTRONIC("electronic", "Electronic", electronicImage),
    AMBIENT("ambient", "Ambient", ambientImage),
    REGGAE("reggae", "Reggae", reggaeImage),
    LATIN("latin", "Latin", latinImage),
    BLUES("blues", "Blues", bluesImage),
    FOLK("folk", "Folk", folkImage),
    METAL("metal", "Metal", metalImage),
    COUNTRY("country", "Country", countryImage),
    SOUNDTRACK("soundtrack", "Soundtrack", soundtrackImage),
    WORLD("world", "World", worldImage),
    DANCE("dance", "Dance", danceImage),
    LOUNGE("lounge", "Lounge", loungeImage),
    PUNK("punk", "Punk", punkImage),
    SOUL("soul", "Soul", soulImage),
    FUNK("funk", "Funk", funkImage);
}