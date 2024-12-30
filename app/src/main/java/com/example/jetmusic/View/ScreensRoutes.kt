package com.example.jetmusic.View

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
    object DetailedMusicRoute {}
}