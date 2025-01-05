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
    object SearchRoute {}

    @Serializable
    object LibraryRoute {}

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

