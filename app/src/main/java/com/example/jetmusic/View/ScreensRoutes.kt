package com.example.jetmusic.View

import com.example.jetmusic.data.enums.Genres.MusicGenres
import kotlinx.serialization.Serializable

@Serializable
object ScreensRoutes {

    @Serializable
    object StartScreens {
        @Serializable
        object WelcomeRoute

        @Serializable
        object SignUpRoute

        @Serializable
        object LogInRoute

        @Serializable
        object ContinueWithPhoneNumber
    }

    @Serializable
    object HomeRoute {}

    @Serializable
    object MainSearchRoute {

        @Serializable
        data class BrowsedMusicListRoute(val genre: String)
        @Serializable
        object DiscoverRoute

        @Serializable
        object SearchedMediaRoute
    }

    @Serializable
    object LibraryNavigationGraph {
        @Serializable
        object LibraryRoute

        @Serializable
        object LikedSongsRoute

        @Serializable
        object LikedPlaylistsRoute

        @Serializable
        object LikedArtistsRoute

        @Serializable
        object DownloadsRoute

        @Serializable
        object ProfileRoute

        @Serializable
        object EditProfileRoute
    }

    @Serializable
    object DetailedScreens {

        @Serializable
        object DetailedMusicRoute {}

        @Serializable
        data class DetailedPlaylistRoute (val parametersJson: String)

        @Serializable
        data class DetailedArtistRoute (val parametersJson: String) {
            companion object{
                @Serializable
                object ArtistMainInfoRoute

                @Serializable
                object ArtistMoreTracksRoute
            }
        }
    }

}

