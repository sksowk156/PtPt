package com.paradise.feature.routine.ui.screen.category.screen.setup.screen.rest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import com.paradise.feature.routine.ui.component.TimePicker

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
    var restMinutes by remember { mutableStateOf((initialConfig?.seconds ?: 60) / 60) }
    var restSeconds by remember { mutableStateOf((initialConfig?.seconds ?: 60) % 60) }

    Column(
        modifier = Modifier.padding(20.dp),
    ) {
        Text(
            "세트 간 휴식을 설정하세요",
            style = PtPtTheme.typography.body02,
        )

        // 이전 설정들 요약 - Count Up일 때는 다르게 표시
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SettingSummaryChip(
                if (repConfig.isCountUp) {
                    "Count Up 모드"
                } else {
                    "${repConfig.count}회 Count Down"
                },
            )
            SettingSummaryChip(
                if (setConfig.isCountUp) {
                    "세트 수 제한 없음"
                } else {
                    "${setConfig.count}세트 목표"
                },
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 휴식 모드 선택
        Text("휴식 방식", style = PtPtTheme.typography.body01)

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            RestModeCard(
                selected = restMode == RestMode.MANUAL,
                icon = PtPtTheme.icon.time,
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
        AnimatedVisibility(
            visible = restMode == RestMode.AUTO,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            Column(
                modifier = Modifier.padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("휴식 시간", style = PtPtTheme.typography.body01)

                Spacer(modifier = Modifier.height(16.dp))

                // 분/초 선택기
                TimePicker(
                    minutes = restMinutes,
                    seconds = restSeconds,
                    onTimeChange = { min, sec ->
                        restMinutes = min
                        restSeconds = sec
                    },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 퀵 선택
                Text("빠른 선택", style = PtPtTheme.typography.caption01)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    TimeChip("30초", restMinutes == 0 && restSeconds == 30) {
                        restMinutes = 0
                        restSeconds = 30
                    }
                    TimeChip("1분", restMinutes == 1 && restSeconds == 0) {
                        restMinutes = 1
                        restSeconds = 0
                    }
                    TimeChip("1분 30초", restMinutes == 1 && restSeconds == 30) {
                        restMinutes = 1
                        restSeconds = 30
                    }
                    TimeChip("2분", restMinutes == 2 && restSeconds == 0) {
                        restMinutes = 2
                        restSeconds = 0
                    }
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
                        if (repConfig.isCountUp) {
                            Text("Count Up 모드")
                            Text("목표 없음")
                        } else {
                            Text("Count Down 모드")
                            Text("${repConfig.count}회 목표")
                        }

                        if (setConfig.isCountUp) {
                            Text("세트 수: 무제한")
                        } else {
                            Text("${setConfig.count}세트 목표")
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(if (restMode == RestMode.AUTO) "자동 휴식" else "수동 휴식")
                        if (restMode == RestMode.AUTO) {
                            restMinutes * 60 + restSeconds
                            Text(
                                if (restMinutes > 0) {
                                    "${restMinutes}분 ${restSeconds}초"
                                } else {
                                    "${restSeconds}초"
                                },
                            )
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
                    val totalSeconds = if (restMode == RestMode.AUTO) {
                        restMinutes * 60 + restSeconds
                    } else {
                        null
                    }

                    onComplete(
                        RestConfig(
                            mode = restMode,
                            seconds = totalSeconds,
                        ),
                    )
                },
                modifier = Modifier.weight(1f),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(imageVector = PtPtTheme.icon.front, contentDescription = "")
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
