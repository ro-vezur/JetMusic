package com.example.jetmusic

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetmusic.View.Screens.MainScreen
import com.example.jetmusic.SharedViewModels.SharedMusicControllerViewModel
import com.example.jetmusic.SharedViewModels.UserViewModel
import com.example.jetmusic.data.Services.MusicService.MusicService
import com.example.jetmusic.ui.theme.JetMusicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val sharedViewModel: SharedMusicControllerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val localUserViewModel = staticCompositionLocalOf<UserViewModel> {
                error("No UserViewModel provided")
            }

            val userViewModel: UserViewModel = hiltViewModel()

            JetMusicTheme {
                CompositionLocalProvider(localUserViewModel provides userViewModel) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreen(
                            sharedMusicControllerViewModel = sharedViewModel
                        )
                    }
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedViewModel.destroyMediaController()
        stopService(Intent(applicationContext,MusicService::class.java))
    }
}
