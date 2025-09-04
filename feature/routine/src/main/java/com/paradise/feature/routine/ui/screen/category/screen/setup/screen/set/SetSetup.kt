package com.paradise.feature.routine.ui.screen.category.screen.setup.screen.set

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import com.paradise.feature.routine.model.RepConfig
import com.paradise.feature.routine.model.SetConfig
import com.paradise.feature.routine.model.SetMode
import com.paradise.feature.routine.ui.component.NumberPicker
import com.paradise.feature.routine.ui.component.QuickChip
import com.paradise.feature.routine.ui.component.SegmentedButton

@Composable
fun SetSetup(
    repConfig: RepConfig,
    initialConfig: SetConfig?,
    onNext: (SetConfig) -> Unit,
    onBack: () -> Unit,
) {
    // 상태 관리
    var sets by remember { mutableStateOf(initialConfig?.count ?: 3) }
    var isSetCountUp by remember { mutableStateOf(initialConfig?.isCountUp ?: true) }

    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "세트 수를 설정하세요",
            style = PtPtTheme.typography.title01,
        )

        // 이전 설정 요약 표시
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = PtPtTheme.color.componentNormal,
            ),
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = PtPtTheme.icon.checkFill,
                    contentDescription = "",
                    tint = PtPtTheme.color.primaryNormal,
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "${repConfig.count}회 ${if (repConfig.isCountUp) "↑" else "↓"}",
                    color = PtPtTheme.color.textNormal,
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 세트 카운트 방식 - SegmentedButton에 맞게 수정
        val setModeOptions = listOf(
            SetMode("↑", "세트 올리기", "1세트부터"),
            SetMode("↓", "세트 내리기", "목표 세트부터"),
        )

        SegmentedButton(
            options = setModeOptions,
            selected = if (isSetCountUp) setModeOptions[0] else setModeOptions[1],
            onSelectionChange = { mode ->
                isSetCountUp = mode.icon == "↑"
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

        // 세트 수 선택
        NumberPicker(
            value = sets,
            onValueChange = { sets = it },
            range = 1..10,
            modifier = Modifier.height(200.dp),
        )

        // 퀵 선택
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            QuickChip("3세트", selected = sets == 3) { sets = 3 }
            QuickChip("4세트", selected = sets == 4) { sets = 4 }
            QuickChip("5세트", selected = sets == 5) { sets = 5 }
        }

        // 버튼들
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.weight(1f),
            ) {
                Text("이전")
            }
            Button(
                onClick = {
                    onNext(SetConfig(count = sets, isCountUp = isSetCountUp))
                },
                modifier = Modifier.weight(1f),
            ) {
                Text("다음")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetSetupPreview() {
    PtPtTheme {
        SetSetup(
            repConfig = RepConfig(count = 15, isCountUp = true),
            initialConfig = null,
            onNext = { },
            onBack = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SetSetupWithInitialConfigPreview() {
    PtPtTheme {
        SetSetup(
            repConfig = RepConfig(count = 20, isCountUp = false),
            initialConfig = SetConfig(count = 5, isCountUp = false),
            onNext = { },
            onBack = { },
        )
    }
}
