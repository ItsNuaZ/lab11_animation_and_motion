package com.example.studentapp.ui.home

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.studentapp.ui.animation.LoadingAnimation

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCameraClick: () -> Unit,
    onMusicClick: () -> Unit,
    onVideoClick: () -> Unit,
    onSensorClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val cardHeight by animateDpAsState(
        targetValue = if (expanded) 220.dp else 120.dp,
        label = "cardHeight"
    )

    var showMessage by remember { mutableStateOf(false) }

    var selected by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = selected, label = "cardTransition")
    val scale by transition.animateFloat(label = "scale") { state ->
        if (state) 1.05f else 1f
    }
    val alpha by transition.animateFloat(label = "alpha") { state ->
        if (state) 1f else 0.85f
    }

    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(animationSpec = tween(1000))
        ) {
            Text(
                "Student Life",
                style = MaterialTheme.typography.headlineLarge
            )
        }

        Spacer(Modifier.height(16.dp))

        if (isLoading) {
            LoadingAnimation()
            Spacer(Modifier.height(16.dp))
            Button(onClick = { isLoading = false }) {
                Text("Stop Loading")
            }
        } else {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    .clickable { selected = !selected },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Hello, Student",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(onClick = { expanded = !expanded }) {
                            Text(if (expanded) "Collapse" else "Expand")
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            AnimatedVisibility(
                visible = showMessage,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text(
                        "🎉 Keep Going!",
                        modifier = Modifier.padding(20.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { showMessage = !showMessage },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (showMessage) "Hide Message" else "Show Message")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { isLoading = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Text("Show Loading Animation")
            }

            Spacer(Modifier.height(24.dp))

            Row {
                Button(
                    onClick = onCameraClick
                ) {
                    Text("📷 Camera Filter")
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = onMusicClick
                ) {
                    Text("🎵 Music Player")
                }
            }

            Spacer(Modifier.height(8.dp))

            Row {
                Button(
                    onClick = onVideoClick
                ) {
                    Text("🎬 Video Player")
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = onSensorClick
                ) {
                    Text("👟 Step Counter")
                }
            }
        }
    }
}
