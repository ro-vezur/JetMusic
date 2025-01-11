package com.example.jetmusic.View.ScreenRoutes

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
    }

    @Serializable
    object HomeRoute {}

    @Serializable
    object MainSearchRoute {
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
        object LikedSongs

        @Serializable
        object LikedPlaylists

        @Serializable
        object LikedArtists

        @Serializable
        object Downloads
    }

    @Serializable
    object ProfileRoute {}

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

