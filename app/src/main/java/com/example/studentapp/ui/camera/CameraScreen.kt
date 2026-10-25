package com.example.studentapp.ui.camera

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    LaunchedEffect(hasCameraPermission) {
        if (hasCameraPermission) {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build()
                preview.surfaceProvider = previewView.surfaceProvider
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(context))
        }
    }

    var filter by remember { mutableStateOf("Original") }

    Box(modifier = Modifier.fillMaxSize()) {
        if (hasCameraPermission) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )

            when (filter) {
                "Warm" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Yellow.copy(alpha = 0.15f))
                    )
                }
                "Cool" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Blue.copy(alpha = 0.15f))
                    )
                }
                "Vintage" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF8B4513).copy(alpha = 0.20f))
                    )
                }
                "Dark" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.30f))
                    )
                }
                "B&W" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray.copy(alpha = 0.25f))
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Camera permission required")
            }
        }

        FlowRow(
            maxItemsInEachRow = 3,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Button(
                onClick = {
                    filter = "Original"
                }
            ) {
                Text("Original")
            }

            Button(
                onClick = {
                    filter = "Warm"
                }
            ) {
                Text("Warm")
            }

            Button(
                onClick = {
                    filter = "B&W"
                }
            ) {
                Text("B&W")
            }

            Button(
                onClick = {
                    filter = "Cool"
                }
            ) {
                Text("Cool")
            }

            Button(
                onClick = {
                    filter = "Vintage"
                }
            ) {
                Text("Vintage")
            }

            Button(
                onClick = {
                    filter = "Dark"
                }
            ) {
                Text("Dark")
            }
        }
    }
}
