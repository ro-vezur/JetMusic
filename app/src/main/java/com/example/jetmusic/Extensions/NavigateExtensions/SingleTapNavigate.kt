package com.example.jetmusic.Extensions.NavigateExtensions

import androidx.navigation.NavController

fun <T: Any> NavController.singleTapNavigate(id: T) {
    this.navigate(id) {
        launchSingleTop = true
        restoreState = true
    }
}