package com.paradise.feature.routine.ui.screen.category.screen.setup.screen.repetition

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.feature.routine.model.CountMode
import com.paradise.feature.routine.model.RepConfig
import com.paradise.feature.routine.ui.component.NumberPicker
import com.paradise.feature.routine.ui.component.QuickChip
import com.paradise.feature.routine.ui.component.SegmentedButton

@Composable
fun RepetitionSetup(
    initialConfig: RepConfig?,
    onNext: (RepConfig) -> Unit,
) {
    var isCountUp by remember { mutableStateOf(initialConfig?.isCountUp ?: true) }
    var repetitions by remember { mutableStateOf(initialConfig?.count ?: 15) }

    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "운동 방식을 선택하세요",
            style = PtPtTheme.typography.body02,
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 카운트 방식 토글
        val countModeOptions = listOf(
            CountMode(
                icon = "↑",
                title = "Count Up",
                subtitle = "0부터 시작, 목표 없음",
            ),
            CountMode(
                icon = "↓",
                title = "Count Down",
                subtitle = "목표 횟수부터 시작",
            ),
        )

        SegmentedButton(
            options = countModeOptions,
            selected = if (isCountUp) countModeOptions[0] else countModeOptions[1],
            onSelectionChange = { mode ->
                isCountUp = mode.icon == "↑"
            },
            modifier = Modifier.fillMaxWidth(),
        ) { mode ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = mode.icon,
                    style = PtPtTheme.typography.body03,
                    color = LocalContentColor.current,
                )
                Text(
                    text = mode.title,
                    style = PtPtTheme.typography.body02,
                    color = LocalContentColor.current,
                )
                Text(
                    text = mode.subtitle,
                    style = PtPtTheme.typography.caption01,
                    color = LocalContentColor.current.copy(alpha = 0.7f),
                )
            }
        }

        // Count Down일 때만 목표 횟수 설정 표시
        AnimatedVisibility(
            visible = !isCountUp,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    "목표 횟수",
                    style = PtPtTheme.typography.body01,
                    color = PtPtTheme.color.textNormal,
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 횟수 선택
                NumberPicker(
                    value = repetitions,
                    onValueChange = { repetitions = it },
                    range = 1..100,
                    modifier = Modifier.height(120.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 퀵 선택 버튼
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    QuickChip("10", selected = repetitions == 10) { repetitions = 10 }
                    QuickChip("15", selected = repetitions == 15) { repetitions = 15 }
                    QuickChip("20", selected = repetitions == 20) { repetitions = 20 }
                    QuickChip("30", selected = repetitions == 30) { repetitions = 30 }
                }
            }
        }

        // Count Up일 때 안내 메시지
        AnimatedVisibility(
            visible = isCountUp,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            Column(
                modifier = Modifier.padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = PtPtTheme.icon.refresh,
                    contentDescription = null,
                    tint = PtPtTheme.color.primaryNormal,
                    modifier = Modifier.size(48.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "0부터 시작하여 원하는 만큼 운동하세요",
                    style = PtPtTheme.typography.body01,
                    textAlign = TextAlign.Center,
                    color = PtPtTheme.color.textNormal,
                )
                Text(
                    "운동 중 언제든지 멈출 수 있습니다",
                    style = PtPtTheme.typography.caption01,
                    textAlign = TextAlign.Center,
                    color = PtPtTheme.color.textAssist,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                onNext(
                    RepConfig(
                        count = if (isCountUp) 0 else repetitions,
                        isCountUp = isCountUp,
                    ),
                )
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("다음")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = PtPtTheme.icon.front,
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepetitionSetupPreview() {
    PtPtTheme {
        RepetitionSetup(
            initialConfig = null,
            onNext = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RepetitionSetupWithInitialConfigPreview() {
    PtPtTheme {
        RepetitionSetup(
            initialConfig = RepConfig(count = 20, isCountUp = false),
            onNext = { },
        )
    }
}
