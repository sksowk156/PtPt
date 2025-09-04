package com.paradise.feature.routine.content.add.content.save

import PrimaryButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.R
import com.paradise.core.designsystem.component.button.SecondaryButton
import com.paradise.core.designsystem.component.textfield.PtPtTextField
import com.paradise.core.designsystem.component.textfield.rememberPtPtTextFieldState
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
internal fun RoutineSaveContent(onSaveComplete: () -> Unit) {
    RoutineSaveContent(
        onSaveComplete = onSaveComplete,
        modifier = Modifier,
    )
}

@Composable
internal fun RoutineSaveContent(
    onSaveComplete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showEditText by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (!showEditText) {
            RoutinePreviewSection()
        } else {
            RoutineEditSection(
                onComplete = { name ->
                    onSaveComplete()
                },
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SecondaryButton(
                modifier = Modifier.weight(1f),
                text = "다시하기",
                onClick = {
                    showEditText = false
                },
            )

            PrimaryButton(
                modifier = Modifier.weight(1f),
                text = if (showEditText) "완료" else "저장 후 다음",
                onClick = {
                    if (!showEditText) {
                        showEditText = true
                    } else {
                        onSaveComplete()
                    }
                },
            )
        }
    }
}

@Composable
fun RoutinePreviewSection(
    imageRes: Int = R.drawable.ic_my,
    title: String = "첫 번째 동작 등록",
    description: String = "이 동작을 저장할까요?",
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "동작 이미지",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
                .height(330.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop,
        )

        Text(
            modifier = Modifier.padding(top = 40.dp),
            text = title,
            color = PtPtTheme.color.textNeutral,
            style = PtPtTheme.typography.body04,
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = description,
            color = PtPtTheme.color.textStrong,
            style = PtPtTheme.typography.title02,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun RoutineEditSection(
    modifier: Modifier = Modifier,
    onComplete: (String) -> Unit,
) {
    val textState = rememberPtPtTextFieldState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "이름을 정해주세요",
            color = PtPtTheme.color.textStrong,
            style = PtPtTheme.typography.title02,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp),
        )

        PtPtTextField(
            state = textState,
            placeholder = "입력하세요",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onSubmit = onComplete,
        )
    }
}

@Preview(
    name = "RoutineSaveContent - Default",
    showBackground = true,
    backgroundColor = 0xFF1C1C1C,
    widthDp = 360,
    heightDp = 640,
)
@Composable
fun PreviewRoutineSaveContent() {
    PtPtTheme {
        RoutineSaveContent(
            onSaveComplete = { /* Preview에서는 아무 동작 안함 */ },
        )
    }
}

@Preview(
    name = "RoutineSaveContent - Tablet",
    showBackground = true,
    backgroundColor = 0xFF1C1C1C,
    widthDp = 600,
    heightDp = 800,
)
@Composable
fun PreviewRoutineSaveContentTablet() {
    PtPtTheme {
        RoutineSaveContent(
            onSaveComplete = { },
            modifier = Modifier,
        )
    }
}

@Preview(
    name = "RoutineSaveContent - Small Screen",
    showBackground = true,
    backgroundColor = 0xFF1C1C1C,
    widthDp = 320,
    heightDp = 480,
)
@Composable
fun PreviewRoutineSaveContentSmall() {
    PtPtTheme {
        RoutineSaveContent(
            onSaveComplete = { },
        )
    }
}

@Preview(
    name = "RoutineSaveContent - Dark Background",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 360,
    heightDp = 640,
)
@Composable
fun PreviewRoutineSaveContentDarkBg() {
    PtPtTheme {
        RoutineSaveContent(
            onSaveComplete = { },
        )
    }
}

@Preview(
    name = "RoutineSaveContent - With System UI",
    showSystemUi = true,
    showBackground = true,
    backgroundColor = 0xFF1C1C1C,
)
@Composable
fun PreviewRoutineSaveContentSystemUI() {
    PtPtTheme {
        RoutineSaveContent(
            onSaveComplete = { },
        )
    }
}

@Preview(
    name = "RoutineSaveContent - Landscape",
    showBackground = true,
    backgroundColor = 0xFF1C1C1C,
    widthDp = 640,
    heightDp = 360,
)
@Composable
fun PreviewRoutineSaveContentLandscape() {
    PtPtTheme {
        RoutineSaveContent(
            onSaveComplete = { },
        )
    }
}

// Interactive Preview (실제 동작 테스트용)
@Preview(
    name = "RoutineSaveContent - Interactive",
    showBackground = true,
    backgroundColor = 0xFF1C1C1C,
    widthDp = 360,
    heightDp = 640,
)
@Composable
fun PreviewRoutineSaveContentInteractive() {
    var isSaved by remember { mutableStateOf(false) }

    PtPtTheme {
        Column {
            RoutineSaveContent(
                onSaveComplete = {
                    isSaved = true
                },
            )

            // 저장 상태 표시 (Preview에서 동작 확인용)
            if (isSaved) {
                Text(
                    text = "저장 완료!",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}

// 다크 모드 Preview
@Preview(
    name = "RoutineSaveContent - Dark Mode",
    showBackground = true,
    backgroundColor = 0xFF1C1C1C,
    widthDp = 360,
    heightDp = 640,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewRoutineSaveContentDarkMode() {
    PtPtTheme {
        RoutineSaveContent(
            onSaveComplete = { },
        )
    }
}
