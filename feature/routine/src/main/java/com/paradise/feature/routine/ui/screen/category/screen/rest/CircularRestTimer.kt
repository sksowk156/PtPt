package com.paradise.feature.routine.ui.screen.category.screen.rest

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paradise.core.designsystem.theme.PtPtTheme
import kotlinx.coroutines.delay

// 원형 휴식 타이머 - 일시정지 기능 추가
@Composable
fun CircularRestTimer(
    timeLeft: Int,
    totalTime: Int,
    onSkip: () -> Unit,
    onPause: () -> Unit = {},
    isPaused: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val progress = remember(timeLeft, totalTime) {
        if (totalTime > 0) timeLeft.toFloat() / totalTime else 0f
    }

    Box(
        modifier = modifier.size(280.dp),
        contentAlignment = Alignment.Center,
    ) {
        // 배경 원
        Canvas(
            modifier = Modifier.fillMaxSize(),
        ) {
            // 배경 트랙
            drawCircle(
                color = Color.White.copy(alpha = 0.1f),
                style = Stroke(width = 12.dp.toPx()),
            )

            // 진행률 아크
            drawArc(
                color = Color.Green,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(
                    width = 12.dp.toPx(),
                    cap = StrokeCap.Round,
                ),
            )
        }

        // 중앙 컨텐츠
        Card(
            modifier = Modifier.size(240.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.Black.copy(alpha = 0.7f),
            ),
            border = BorderStroke(
                width = 2.dp,
                color = if (isPaused) {
                    PtPtTheme.color.assistRed.copy(alpha = 0.3f)
                } else {
                    PtPtTheme.color.primaryNormal.copy(alpha = 0.3f)
                },
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = if (isPaused) "일시정지" else "휴식 시간",
                    style = PtPtTheme.typography.body02,
                    color = if (isPaused) {
                        PtPtTheme.color.assistRed
                    } else {
                        Color.White.copy(alpha = 0.8f)
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 시간 표시
                Text(
                    text = formatTime(timeLeft),
                    style = TextStyle(
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    ),
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 진행률 퍼센트
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = PtPtTheme.typography.body01,
                    color = if (isPaused) {
                        PtPtTheme.color.assistRed
                    } else {
                        PtPtTheme.color.primaryNormal
                    },
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 컨트롤 버튼들
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // 일시정지/재개 버튼
                    IconButton(
                        onClick = onPause,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                if (isPaused) {
                                    PtPtTheme.color.primaryNormal.copy(alpha = 0.2f)
                                } else {
                                    Color.White.copy(alpha = 0.1f)
                                },
                            ),
                    ) {
                        Icon(
                            imageVector = if (isPaused) {
                                PtPtTheme.icon.plus
                            } else {
                                PtPtTheme.icon.pause
                            },
                            contentDescription = if (isPaused) "재개" else "일시정지",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp),
                        )
                    }

                    // 건너뛰기 버튼
                    OutlinedButton(
                        onClick = onSkip,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White,
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color.White.copy(alpha = 0.3f),
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.height(48.dp),
                    ) {
                        Text("건너뛰기")
                    }
                }
            }
        }
    }
}

// 수동 휴식 시간 설정 화면
@Composable
fun ManualRestTimeSetting(
    onConfirm: (Int) -> Unit,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var minutes by remember { mutableStateOf(1) }
    var seconds by remember { mutableStateOf(0) }

    Card(
        modifier = modifier
            .fillMaxWidth(0.85f)
            .wrapContentHeight(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.9f),
        ),
        border = BorderStroke(
            width = 1.dp,
            color = PtPtTheme.color.primaryNormal.copy(alpha = 0.3f),
        ),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "휴식 시간 설정",
                style = PtPtTheme.typography.body01,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 시간 선택기
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 분 선택
                TimePickerColumn(
                    value = minutes,
                    onValueChange = { minutes = it },
                    range = 0..5,
                    label = "분",
                )

                Text(
                    text = ":",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp),
                )

                // 초 선택 (5초 단위)
                TimePickerColumn(
                    value = seconds,
                    onValueChange = { seconds = it },
                    range = 0..55 step 5,
                    label = "초",
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 빠른 선택 버튼들
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                QuickTimeChip(
                    text = "30초",
                    selected = minutes == 0 && seconds == 30,
                    onClick = {
                        minutes = 0
                        seconds = 30
                    },
                    modifier = Modifier.weight(1f),
                )
                QuickTimeChip(
                    text = "1분",
                    selected = minutes == 1 && seconds == 0,
                    onClick = {
                        minutes = 1
                        seconds = 0
                    },
                    modifier = Modifier.weight(1f),
                )
                QuickTimeChip(
                    text = "1분 30초",
                    selected = minutes == 1 && seconds == 30,
                    onClick = {
                        minutes = 1
                        seconds = 30
                    },
                    modifier = Modifier.weight(1f),
                )
                QuickTimeChip(
                    text = "2분",
                    selected = minutes == 2 && seconds == 0,
                    onClick = {
                        minutes = 2
                        seconds = 0
                    },
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 액션 버튼들
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedButton(
                    onClick = onSkip,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White,
                    ),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
                    modifier = Modifier.weight(1f),
                ) {
                    Text("건너뛰기")
                }

                Button(
                    onClick = {
                        val totalSeconds = minutes * 60 + seconds
                        if (totalSeconds > 0) {
                            onConfirm(totalSeconds)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PtPtTheme.color.primaryNormal,
                    ),
                    modifier = Modifier.weight(1f),
                ) {
                    Text("시작")
                }
            }
        }
    }
}

// 시간 선택 컬럼
@Composable
fun TimePickerColumn(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntProgression,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = label,
            style = PtPtTheme.typography.caption01,
            color = Color.White.copy(alpha = 0.6f),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 증가 버튼
            IconButton(
                onClick = {
                    val next = value + range.step
                    if (next <= range.last) {
                        onValueChange(next)
                    }
                },
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    imageVector = PtPtTheme.icon.plus,
                    contentDescription = "증가",
                    tint = if (value < range.last) Color.White else Color.White.copy(alpha = 0.3f),
                    modifier = Modifier.size(16.dp),
                )
            }

            // 값 표시
            Box(
                modifier = Modifier
                    .size(width = 60.dp, height = 60.dp)
                    .background(
                        Color.White.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = value.toString().padStart(2, '0'),
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    ),
                )
            }

            // 감소 버튼
            IconButton(
                onClick = {
                    val prev = value - range.step
                    if (prev >= range.first) {
                        onValueChange(prev)
                    }
                },
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    imageVector = PtPtTheme.icon.down,
                    contentDescription = "감소",
                    tint = if (value > range.first) Color.White else Color.White.copy(alpha = 0.3f),
                    modifier = Modifier.size(16.dp),
                )
            }
        }
    }
}

// 빠른 시간 선택 칩
@Composable
fun QuickTimeChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (selected) {
                    PtPtTheme.color.primaryNormal
                } else {
                    Color.White.copy(alpha = 0.1f)
                },
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = PtPtTheme.typography.caption01,
            color = if (selected) Color.White else Color.White.copy(alpha = 0.6f),
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        )
    }
}

// 헬퍼 함수들
fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%d:%02d".format(minutes, remainingSeconds)
}

// 1. CircularRestTimer 기본 상태
@Preview(name = "Rest Timer States")
@Composable
fun CircularRestTimerPreview() {
    PtPtTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // 진행 중
            CircularRestTimer(
                timeLeft = 45,
                totalTime = 60,
                isPaused = false,
                onSkip = {},
                onPause = {},
            )

            // 일시정지 상태
            CircularRestTimer(
                timeLeft = 30,
                totalTime = 60,
                isPaused = true,
                onSkip = {},
                onPause = {},
            )
        }
    }
}

// 2. 수동 휴식 시간 설정 화면
@Preview(name = "Manual Rest Time Setting")
@Composable
fun ManualRestTimeSettingPreview() {
    PtPtTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center,
        ) {
            ManualRestTimeSetting(
                onConfirm = { seconds ->
                    println("Selected: $seconds seconds")
                },
                onSkip = {},
            )
        }
    }
}

// 3. 통합 시뮬레이션 - 수동 모드 플로우
@Preview(name = "Rest Flow Interactive", showSystemUi = true)
@Composable
fun RestFlowInteractivePreview() {
    PtPtTheme {
        var showTimeSetting by remember { mutableStateOf(true) }
        var restTimeLeft by remember { mutableStateOf(0) }
        var totalTime by remember { mutableStateOf(0) }
        var isPaused by remember { mutableStateOf(false) }

        // 타이머 카운트다운
        LaunchedEffect(restTimeLeft, isPaused) {
            if (restTimeLeft > 0 && !isPaused) {
                delay(1000)
                restTimeLeft--
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center,
        ) {
            if (showTimeSetting && restTimeLeft == 0) {
                // 시간 설정 화면
                ManualRestTimeSetting(
                    onConfirm = { seconds ->
                        restTimeLeft = seconds
                        totalTime = seconds
                        showTimeSetting = false
                    },
                    onSkip = {
                        showTimeSetting = false
                    },
                )
            } else if (restTimeLeft > 0) {
                // 타이머 화면
                CircularRestTimer(
                    timeLeft = restTimeLeft,
                    totalTime = totalTime,
                    isPaused = isPaused,
                    onSkip = {
                        restTimeLeft = 0
                        showTimeSetting = true
                    },
                    onPause = { isPaused = !isPaused },
                )
            } else {
                // 리셋 버튼
                Button(
                    onClick = { showTimeSetting = true },
                ) {
                    Text("휴식 시작")
                }
            }
        }
    }
}
