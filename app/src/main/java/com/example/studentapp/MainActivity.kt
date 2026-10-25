package com.example.studentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.studentapp.ui.camera.CameraScreen
import com.example.studentapp.ui.home.HomeScreen
import com.example.studentapp.ui.music.MusicScreen
import com.example.studentapp.ui.sensor.StepCounterScreen
import com.example.studentapp.ui.theme.StudentAppTheme
import com.example.studentapp.ui.video.VideoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentAppTheme {
                var currentScreen by remember { mutableStateOf("home") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AnimatedContent(
                            targetState = currentScreen,
                            transitionSpec = {
                                (fadeIn(animationSpec = tween(300)) + slideInHorizontally(animationSpec = tween(300)) { it / 2 })
                                    .togetherWith(fadeOut(animationSpec = tween(300)) + slideOutHorizontally(animationSpec = tween(300)) { -it / 2 })
                            },
                            label = "screenTransition"
                        ) { screen ->
                            when (screen) {
                                "home" -> HomeScreen(
                                    onCameraClick = { currentScreen = "camera" },
                                    onMusicClick = { currentScreen = "music" },
                                    onVideoClick = { currentScreen = "video" },
                                    onSensorClick = { currentScreen = "sensor" }
                                )
                                "camera" -> CameraScreen()
                                "music" -> MusicScreen()
                                "video" -> VideoScreen()
                                "sensor" -> StepCounterScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
