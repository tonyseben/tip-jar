package com.example.tipjar.ui.camera

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tipjar.ui.camera.component.CameraPreview
import com.example.tipjar.ui.camera.component.ImageBottomSheet
import com.example.tipjar.ui.camera.component.SnapButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun CameraScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel: CameraViewModel = hiltViewModel()

    var isProgress by remember { mutableStateOf(false) }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }
    if (!hasCameraPermission) {
        SideEffect {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }


    val state by viewModel.state.collectAsState()

    var showSheet by remember { mutableStateOf(false) }
    if (showSheet) {
        state.bitmap?.let { bitmap ->
            ImageBottomSheet(
                bitmap = bitmap,
                isProgress = isProgress,
                onSaveClick = {
                    scope.launch {
                        isProgress = true
                        saveBitmapToInternalStorage(
                            context,
                            bitmap,
                            state.timestamp.toString()
                        ).collect { path ->
                            isProgress = false
                            viewModel.setEvent(CameraContract.Event.SaveReceipt(path))
                        }
                    }
                },
                onDiscardClick = {
                    viewModel.setEvent(CameraContract.Event.OnDiscardClick)
                }
            )
        }
    }
    LaunchedEffect(state) { showSheet = state.bitmap != null }
    LaunchedEffect(Unit) {
        navController.previousBackStackEntry?.savedStateHandle?.set("isReceiptSaved", false)
        viewModel.effect.collect { effect ->
            when (effect) {
                CameraContract.SideEffect.ReceiptSaveSuccess -> {
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "isReceiptSaved",
                        true
                    )
                    navController.popBackStack()
                }

                CameraContract.SideEffect.ReceiptSaveFailed -> {
                    navController.popBackStack()
                }
            }
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            CameraPreview(
                controller = controller,
                modifier = Modifier
                    .fillMaxSize()
            )

            SnapButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                onClick = {
                    takePhoto(
                        context,
                        controller = controller,
                        onPhotoTaken = {
                            viewModel.setEvent(CameraContract.Event.OnPhotoTaken(it))
                        }
                    )
                }
            )
        }
    }
}

private fun takePhoto(
    context: Context,
    controller: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit
) {
    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)

                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                }
                val rotatedBitmap = Bitmap.createBitmap(
                    image.toBitmap(),
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )

                onPhotoTaken(rotatedBitmap)
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera", "Couldn't take photo: ", exception)
            }
        }
    )
}

fun saveBitmapToInternalStorage(
    context: Context,
    bitmap: Bitmap,
    fileName: String?
): Flow<String?> = flow {

    Log.d("TEST", "saveBitmapToInternalStorage $fileName")

    val directory = File(context.filesDir, "images")
    if (!directory.exists()) {
        directory.mkdirs()
    }

    var path: String? = null
    if (fileName != null) {
        val file = File(directory, "$fileName.png")
        var fileOutputStream: FileOutputStream? = null

        try {
            fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            path = file.absolutePath

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fileOutputStream?.close()
        }
    }
    Log.d("TEST", "saveBitmapToInternalStorage $path")
    emit(path)
}.flowOn(Dispatchers.IO)