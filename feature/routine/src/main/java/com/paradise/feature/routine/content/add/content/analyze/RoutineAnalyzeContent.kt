package com.paradise.feature.routine.content.add.content.analyze

import PrimaryButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
internal fun RoutineAnalyzeContent(onAnalyzeComplete: () -> Unit) {
    RoutineAnalyzeContent(
        onAnalyzeComplete = onAnalyzeComplete,
        modifier = Modifier,
    )
}

@Composable
internal fun RoutineAnalyzeContent(
    onAnalyzeComplete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
        ) {
            // CameraX Preview
//        AndroidView(
//            factory = { context ->
//                val previewView = PreviewView(context)
//                val executor = ContextCompat.getMainExecutor(context)
//                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
//
//                cameraProviderFuture.addListener(
//                    {
//                        val cameraProvider = cameraProviderFuture.get()
//                        val preview = Preview.Builder().build().also {
//                            it.setSurfaceProvider(previewView.surfaceProvider)
//                        }
//                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//                        cameraProvider.unbindAll()
//                        cameraProvider.bindToLifecycle(
//                            context as LifecycleOwner,
//                            cameraSelector,
//                            preview,
//                        )
//                    },
//                    executor,
//                )
//
//                previewView
//            },
//            modifier = Modifier.fillMaxSize(),
//        )

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "10초 후에 2초 동안\n자세를 유지하세요",
                color = Color.White,
                textAlign = TextAlign.Center,
                style = PtPtTheme.typography.body01,
            )

            PrimaryButton(
                modifier = Modifier
                    .padding(54.dp)
                    .align(Alignment.BottomCenter),
                text = "시작하기",
                onClick = {},
            )
        }
    }
}

@Preview(
    name = "RoutineAnalyzeContent - Default",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
)
@Composable
fun PreviewRoutineAnalyzeContent() {
    PtPtTheme {
        RoutineAnalyzeContent(
            onAnalyzeComplete = { /* Preview에서는 아무 동작 안함 */ },
        )
    }
}

@Preview(
    name = "RoutineAnalyzeContent - With Modifier",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
)
@Composable
fun PreviewRoutineAnalyzeContentWithModifier() {
    PtPtTheme {
        RoutineAnalyzeContent(
            onAnalyzeComplete = { /* Preview에서는 아무 동작 안함 */ },
            modifier = Modifier,
        )
    }
}

@Preview(
    name = "RoutineAnalyzeContent - Dark Mode",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewRoutineAnalyzeContentDark() {
    PtPtTheme {
        RoutineAnalyzeContent(
            onAnalyzeComplete = { },
        )
    }
}
