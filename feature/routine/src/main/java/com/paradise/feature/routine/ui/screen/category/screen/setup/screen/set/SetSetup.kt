package com.paradise.feature.routine.ui.screen.category.screen.setup.screen.set

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedButton
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
    var isSetCountUp by remember { mutableStateOf(initialConfig?.isCountUp ?: true) }
    var sets by remember { mutableStateOf(initialConfig?.count ?: 3) }

    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "세트 설정",
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
                    if (repConfig.isCountUp) {
                        "Count Up 모드 (목표 없음)"
                    } else {
                        "${repConfig.count}회 Count Down"
                    },
                    color = PtPtTheme.color.textNormal,
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 세트 카운트 방식
        val setModeOptions = listOf(
            SetMode("∞", "무제한", "원하는 만큼"),
            SetMode("🎯", "목표 설정", "세트 수 지정"),
        )

        SegmentedButton(
            options = setModeOptions,
            selected = if (isSetCountUp) setModeOptions[0] else setModeOptions[1],
            onSelectionChange = { mode ->
                isSetCountUp = mode.icon == "∞"
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

        // 목표 설정일 때만 세트 수 선택
        AnimatedVisibility(
            visible = !isSetCountUp,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    "목표 세트 수",
                    style = PtPtTheme.typography.body01,
                    color = PtPtTheme.color.textNormal,
                )

                Spacer(modifier = Modifier.height(16.dp))

                NumberPicker(
                    value = sets,
                    onValueChange = { sets = it },
                    range = 1..10,
                    modifier = Modifier.height(120.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 퀵 선택
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    QuickChip("3세트", selected = sets == 3) { sets = 3 }
                    QuickChip("4세트", selected = sets == 4) { sets = 4 }
                    QuickChip("5세트", selected = sets == 5) { sets = 5 }
                }
            }
        }

        // 무제한일 때 안내 메시지
        AnimatedVisibility(
            visible = isSetCountUp,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            Column(
                modifier = Modifier.padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = PtPtTheme.icon.homeOn,
                    contentDescription = null,
                    tint = PtPtTheme.color.primaryNormal,
                    modifier = Modifier.size(48.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "원하는 만큼 세트를 진행하세요",
                    style = PtPtTheme.typography.body01,
                    textAlign = TextAlign.Center,
                    color = PtPtTheme.color.textNormal,
                )
                Text(
                    "운동 중 언제든지 종료할 수 있습니다",
                    style = PtPtTheme.typography.caption01,
                    textAlign = TextAlign.Center,
                    color = PtPtTheme.color.textAssist,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

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
                    onNext(
                        SetConfig(
                            count = if (isSetCountUp) 0 else sets,
                            isCountUp = isSetCountUp,
                        ),
                    )
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
