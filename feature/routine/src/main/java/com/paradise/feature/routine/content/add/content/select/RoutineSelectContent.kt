package com.paradise.feature.routine.content.add.content.select

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.button.SecondaryButton
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
fun RoutineSelectContent(onPoseCountSelected: (Int) -> Unit) {
    Column {
        Text(
            text = "몇 가지 동작을 등록할까요?",
            color = PtPtTheme.color.textStrong,
            style = PtPtTheme.typography.title01,
            modifier = Modifier
                .padding(top = 43.dp)
                .padding(horizontal = 20.dp),
        )

        Text(
            text = "설명글을 넣습니다.",
            color = PtPtTheme.color.textNormal,
            style = PtPtTheme.typography.body02,
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 20.dp),
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(end = 2.dp),
                text = "예시 보러 가기",
                color = PtPtTheme.color.textNeutral,
                style = PtPtTheme.typography.caption01,
            )

            Icon(
                imageVector = PtPtTheme.icon.front,
                contentDescription = "",
                tint = PtPtTheme.color.textStrong,
                modifier = Modifier.size(10.dp),
            )
        }

        Spacer(Modifier.weight(1f))

        SecondaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = "한 가지",
            onClick = {
                onPoseCountSelected(1)
            },
        )

        Spacer(Modifier.height(12.dp))

        SecondaryButton(
            text = "두 가지",
            onClick = {
                onPoseCountSelected(2)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )

        Spacer(Modifier.height(12.dp))

        SecondaryButton(
            text = "세 가지",
            onClick = {
                onPoseCountSelected(3)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )

        Spacer(Modifier.height(40.dp))
    }
}

@Preview(
    name = "RoutineSelectContent - Default",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 360,
    heightDp = 640,
)
@Composable
fun PreviewRoutineSelectContent() {
    PtPtTheme {
        RoutineSelectContent(
            onPoseCountSelected = { count ->
                println("Selected pose count: $count")
            },
        )
    }
}

@Preview(
    name = "RoutineSelectContent - Tablet",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 600,
    heightDp = 800,
)
@Composable
fun PreviewRoutineSelectContentTablet() {
    PtPtTheme {
        RoutineSelectContent(
            onPoseCountSelected = { },
        )
    }
}

@Preview(
    name = "RoutineSelectContent - Small Screen",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 320,
    heightDp = 480,
)
@Composable
fun PreviewRoutineSelectContentSmall() {
    PtPtTheme {
        RoutineSelectContent(
            onPoseCountSelected = { },
        )
    }
}

// 다크 모드 Preview
@Preview(
    name = "RoutineSelectContent - Dark Mode",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 360,
    heightDp = 640,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewRoutineSelectContentDark() {
    PtPtTheme {
        RoutineSelectContent(
            onPoseCountSelected = { },
        )
    }
}

// 시스템 UI 포함 Preview (상태바, 네비게이션 바 포함)
@Preview(
    name = "RoutineSelectContent - With System UI",
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun PreviewRoutineSelectContentSystemUI() {
    PtPtTheme {
        RoutineSelectContent(
            onPoseCountSelected = { },
        )
    }
}

// Interactive Preview를 위한 Wrapper (실제 클릭 동작 테스트용)
@Preview(
    name = "RoutineSelectContent - Interactive",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
)
@Composable
fun PreviewRoutineSelectContentInteractive() {
    var selectedCount by remember { mutableStateOf(0) }

    PtPtTheme {
        Column {
            RoutineSelectContent(
                onPoseCountSelected = { count ->
                    selectedCount = count
                },
            )

            // 선택된 개수를 표시 (Preview에서 동작 확인용)
            if (selectedCount > 0) {
                Text(
                    text = "선택된 동작 개수: $selectedCount",
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}
