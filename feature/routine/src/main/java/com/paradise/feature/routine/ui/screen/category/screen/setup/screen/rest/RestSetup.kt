package com.paradise.feature.routine.ui.screen.category.screen.setup.screen.rest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Slider
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
import com.paradise.feature.routine.model.RestConfig
import com.paradise.feature.routine.model.RestMode
import com.paradise.feature.routine.model.SetConfig
import com.paradise.feature.routine.ui.component.RestModeCard
import com.paradise.feature.routine.ui.component.SettingSummaryChip
import com.paradise.feature.routine.ui.component.TimeChip

@Composable
fun RestSetup(
    repConfig: RepConfig,
    setConfig: SetConfig,
    initialConfig: RestConfig?,
    onComplete: (RestConfig) -> Unit,
    onBack: () -> Unit,
) {
    // 상태 관리
    var restMode by remember { mutableStateOf(initialConfig?.mode ?: RestMode.MANUAL) }
    var restSeconds by remember { mutableStateOf(initialConfig?.seconds ?: 60) }

    Column(
        modifier = Modifier.padding(20.dp),
    ) {
        Text(
            "세트 간 휴식을 설정하세요",
            style = PtPtTheme.typography.body02,
        )

        // 이전 설정들 요약
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SettingSummaryChip("${repConfig.count}회 ${if (repConfig.isCountUp) "↑" else "↓"}")
            SettingSummaryChip("${setConfig.count}세트 ${if (setConfig.isCountUp) "↑" else "↓"}")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 휴식 모드 선택
        Text("휴식 방식", style = PtPtTheme.typography.body01)

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            RestModeCard(
                selected = restMode == RestMode.MANUAL,
                icon = PtPtTheme.icon.location,
                title = "수동 휴식",
                description = "준비되면 직접 다음 세트 시작",
                onClick = { restMode = RestMode.MANUAL },
            )

            RestModeCard(
                selected = restMode == RestMode.AUTO,
                icon = PtPtTheme.icon.time,
                title = "자동 휴식",
                description = "설정한 시간 후 자동 시작",
                onClick = { restMode = RestMode.AUTO },
            )
        }

        // 자동 휴식 선택 시 시간 설정
        AnimatedVisibility(visible = restMode == RestMode.AUTO) {
            Column(
                modifier = Modifier.padding(top = 20.dp),
            ) {
                Text("휴식 시간", style = PtPtTheme.typography.body01)

                // 시간 선택 슬라이더
                Slider(
                    value = restSeconds.toFloat(),
                    onValueChange = { restSeconds = it.toInt() },
                    valueRange = 10f..180f,
                    steps = 16,
                )

                Text(
                    "${restSeconds}초",
                    style = PtPtTheme.typography.body03,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )

                // 퀵 선택
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    TimeChip("30초", restSeconds == 30) { restSeconds = 30 }
                    TimeChip("60초", restSeconds == 60) { restSeconds = 60 }
                    TimeChip("90초", restSeconds == 90) { restSeconds = 90 }
                    TimeChip("2분", restSeconds == 120) { restSeconds = 120 }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 최종 요약 카드
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = PtPtTheme.color.primaryNormal.copy(alpha = 0.1f),
            ),
            border = BorderStroke(1.dp, PtPtTheme.color.primaryPressed),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text("운동 요약", style = PtPtTheme.typography.body02)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text("총 ${repConfig.count * setConfig.count}회")
                        Text("${setConfig.count}세트 × ${repConfig.count}회")
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(if (restMode == RestMode.AUTO) "자동 휴식" else "수동 휴식")
                        if (restMode == RestMode.AUTO) {
                            Text("${restSeconds}초")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 버튼들
        Row(
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
                    val finalRestConfig = RestConfig(
                        mode = restMode,
                        seconds = if (restMode == RestMode.AUTO) restSeconds else null,
                    )
                    onComplete(finalRestConfig)
                },
                modifier = Modifier.weight(1f),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(imageVector = PtPtTheme.icon.plus, contentDescription = "")
                    Spacer(Modifier.width(8.dp))
                    Text("운동 시작")
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 800)
@Composable
fun RestSetupManualPreview() {
    PtPtTheme {
        RestSetup(
            repConfig = RepConfig(count = 15, isCountUp = true),
            setConfig = SetConfig(count = 3, isCountUp = true),
            initialConfig = RestConfig(mode = RestMode.MANUAL, seconds = null),
            onComplete = { },
            onBack = { },
        )
    }
}

@Preview(showBackground = true, heightDp = 800)
@Composable
fun RestSetupAutoPreview() {
    PtPtTheme {
        RestSetup(
            repConfig = RepConfig(count = 20, isCountUp = false),
            setConfig = SetConfig(count = 4, isCountUp = true),
            initialConfig = RestConfig(mode = RestMode.AUTO, seconds = 60),
            onComplete = { },
            onBack = { },
        )
    }
}
