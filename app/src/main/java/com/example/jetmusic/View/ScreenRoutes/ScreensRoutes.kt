package com.example.jetmusic.View.ScreenRoutes

import android.os.Parcelable
import com.example.jetmusic.View.ScreenRoutes.ScreenRoutesAdditionalParameters.DetailedPlaylistRouteParameters
import com.example.jetmusic.data.DTOs.API.MusicDTOs.MusicObject
import kotlinx.parcelize.Parcelize
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
    }

}

