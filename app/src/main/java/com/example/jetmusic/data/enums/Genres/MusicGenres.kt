package com.example.jetmusic.data.enums.Genres

import com.example.jetmusic.R

enum class MusicGenres(val tag: String, val displayName: String, val image: Int) {
    ROCK("rock", "Rock", R.drawable.img_rock_music),
    POP("pop", "Pop", R.drawable.img_pop_music),
    JAZZ("jazz", "Jazz", R.drawable.img_jazz_music),
    CLASSICAL("classical", "Classical", R.drawable.img_classical_music),
    HIPHOP("hiphop", "Hip-Hop", R.drawable.img_hiphop_music),
    ELECTRONIC("electronic", "Electronic", R.drawable.img_electronic_music),
    AMBIENT("ambient", "Ambient", R.drawable.img_ambient_music),
    REGGAE("reggae", "Reggae", R.drawable.img_reggae_music),
    LATIN("latin", "Latin", R.drawable.img_latin_music),
    BLUES("blues", "Blues", R.drawable.img_blues_music),
    FOLK("folk", "Folk", R.drawable.img_folk_music),
    METAL("metal", "Metal", R.drawable.img_metal_music),
    COUNTRY("country", "Country", R.drawable.img_country_music),
    SOUNDTRACK("soundtrack", "Soundtrack", R.drawable.img_soundtrack_music),
    WORLD("world", "World", R.drawable.img_world_music),
    DANCE("dance", "Dance", R.drawable.img_dance_music),
    LOUNGE("lounge", "Lounge", R.drawable.img_lounge_music),
    PUNK("punk", "Punk", R.drawable.img_punk_music),
    SOUL("soul", "Soul", R.drawable.img_soul_music),
    FUNK("funk", "Funk", R.drawable.img_funk_music);
}