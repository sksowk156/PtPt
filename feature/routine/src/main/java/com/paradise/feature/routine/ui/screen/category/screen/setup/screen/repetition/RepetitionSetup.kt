package com.paradise.feature.routine.ui.screen.category.screen.setup.screen.repetition

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
    var repetitions by remember { mutableStateOf(initialConfig?.count ?: 15) }
    var isCountUp by remember { mutableStateOf(initialConfig?.isCountUp ?: true) }

    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "운동 횟수를 설정하세요",
            style = PtPtTheme.typography.body02,
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 카운트 방식 토글
        val countModeOptions = listOf(
            CountMode(
                icon = "↑",
                title = "Count Up",
                subtitle = "0에서 시작",
            ),
            CountMode(
                icon = "↓",
                title = "Count Down",
                subtitle = "목표에서 시작",
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
                    color = PtPtTheme.color.textNormal,
                )
                Text(
                    text = mode.title,
                    style = PtPtTheme.typography.body02,
                    color = PtPtTheme.color.textNormal,
                )
                Text(
                    text = mode.subtitle,
                    style = PtPtTheme.typography.caption01,
                    color = PtPtTheme.color.textAssist,
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 횟수 선택
        NumberPicker(
            value = repetitions,
            onValueChange = { repetitions = it },
            range = 1..100,
            modifier = Modifier.height(200.dp),
        )

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

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                onNext(RepConfig(count = repetitions, isCountUp = isCountUp))
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
