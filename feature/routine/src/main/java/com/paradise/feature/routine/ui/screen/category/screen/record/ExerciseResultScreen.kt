package com.paradise.feature.routine.ui.screen.category.screen.record

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paradise.core.designsystem.component.icon.PtPtIconButton
import com.paradise.core.designsystem.component.topappbar.MainTopAppBar
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.feature.routine.model.ExerciseResult
import com.paradise.feature.routine.model.SetDetail
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ExerciseResultScreen(
    result: ExerciseResult,
    onSave: () -> Unit,
    onDiscard: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PtPtTheme.color.backgroundNormal),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            // 상단 앱바
            MainTopAppBar(
                title = "운동 완료",
                leftContent = {
                    PtPtIconButton(
                        imageVector = PtPtTheme.icon.back,
                        tint = PtPtTheme.color.textNormal,
                        onClick = onDiscard,
                    )
                },
            )

            // 스크롤 가능한 컨텐츠
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp,
                    bottom = 100.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // 1. 메인 정보 (가장 중요)
                item {
                    MainResultCard(result)
                }

                // 2. 핵심 성과 지표
                item {
                    PerformanceMetricsCard(result)
                }

                // 3. 시간 정보
                item {
                    TimeInfoCard(result)
                }

                // 4. 휴식 정보
                item {
                    RestInfoCard(result)
                }

                // 5. 세트별 상세 기록
                item {
                    Text(
                        text = "세트별 기록",
                        style = PtPtTheme.typography.body01,
                        color = PtPtTheme.color.textNormal,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                    )
                }

                itemsIndexed(result.setDetails) { index, setDetail ->
                    SetDetailItem(
                        setDetail = setDetail,
                        isLast = index == result.setDetails.lastIndex,
                    )
                }
            }
        }

        // 하단 고정 버튼
        BottomActionButtons(
            onDiscard = onDiscard,
            onSave = onSave,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
        )
    }
}

// 메인 결과 카드
@Composable
fun MainResultCard(result: ExerciseResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = PtPtTheme.color.primaryNormal,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = result.exerciseName,
                style = PtPtTheme.typography.body02,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 총 운동 시간 (큰 표시)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        imageVector = PtPtTheme.icon.time,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = formatDuration(result.totalDuration),
                        style = PtPtTheme.typography.body01,
                        color = Color.White,
                        fontSize = 48.sp,
                    )
                }
                Text(
                    text = "총 운동 시간",
                    style = PtPtTheme.typography.body02,
                    color = Color.White.copy(alpha = 0.8f),
                )
            }
        }
    }
}

// 성과 지표 카드
@Composable
fun PerformanceMetricsCard(result: ExerciseResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = PtPtTheme.color.componentNormal,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            // 총 세트
            MetricItem(
                icon = PtPtTheme.icon.refresh,
                value = "${result.totalSets}",
                label = "세트",
                color = PtPtTheme.color.primaryNormal,
            )

            Divider(
                modifier = Modifier
                    .height(60.dp)
                    .width(1.dp),
                color = PtPtTheme.color.textNormal,
            )

            // 총 횟수
            MetricItem(
                icon = PtPtTheme.icon.checkFill,
                value = "${result.totalReps}",
                label = "총 횟수",
                color = PtPtTheme.color.primaryNormal,
            )

            Divider(
                modifier = Modifier
                    .height(60.dp)
                    .width(1.dp),
                color = PtPtTheme.color.textNormal,
            )

            // 평균 횟수
            MetricItem(
                icon = PtPtTheme.icon.refresh,
                value = "%.1f".format(result.averageRepsPerSet),
                label = "평균",
                color = PtPtTheme.color.assistBlue,
            )
        }
    }
}

// 개별 지표 아이템
@Composable
fun MetricItem(
    icon: ImageVector,
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            style = PtPtTheme.typography.body03,
            color = PtPtTheme.color.textNormal,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = label,
            style = PtPtTheme.typography.caption01,
            color = PtPtTheme.color.textAssist,
        )
    }
}

// 시간 정보 카드
@Composable
fun TimeInfoCard(result: ExerciseResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = PtPtTheme.color.componentNormal,
        ),
        border = BorderStroke(1.dp, PtPtTheme.color.textNormal),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = PtPtTheme.icon.calendarOn,
                        contentDescription = null,
                        tint = PtPtTheme.color.textAssist,
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = result.startTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                        style = PtPtTheme.typography.body02,
                        color = PtPtTheme.color.textNormal,
                    )
                }

                Text(
                    text = "${result.startTime.format(DateTimeFormatter.ofPattern("HH:mm"))} - ${
                        result.endTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                    }",
                    style = PtPtTheme.typography.body02,
                    color = PtPtTheme.color.textAssist,
                )
            }
        }
    }
}

// 휴식 정보 카드
@Composable
fun RestInfoCard(result: ExerciseResult) {
    if (result.totalRestTime > 0) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = PtPtTheme.color.primaryPressed,
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                // 총 휴식 시간
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = PtPtTheme.icon.pause,
                            contentDescription = null,
                            tint = PtPtTheme.color.textAssist,
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "총 휴식",
                            style = PtPtTheme.typography.caption01,
                            color = PtPtTheme.color.textAssist,
                        )
                    }
                    Text(
                        text = formatDuration(result.totalRestTime),
                        style = PtPtTheme.typography.body01,
                        color = PtPtTheme.color.textNormal,
                        fontWeight = FontWeight.Bold,
                    )
                }

                // 평균 휴식 시간
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = PtPtTheme.icon.refresh,
                            contentDescription = null,
                            tint = PtPtTheme.color.textAssist,
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "평균 휴식",
                            style = PtPtTheme.typography.caption01,
                            color = PtPtTheme.color.textAssist,
                        )
                    }
                    Text(
                        text = formatDuration(result.averageRestTime.toInt()),
                        style = PtPtTheme.typography.body01,
                        color = PtPtTheme.color.textNormal,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

// 세트별 상세 아이템
@Composable
fun SetDetailItem(
    setDetail: SetDetail,
    isLast: Boolean,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isLast) {
                PtPtTheme.color.primaryNormal.copy(alpha = 0.1f)
            } else {
                PtPtTheme.color.componentNormal
            },
        ),
        border = if (isLast) {
            BorderStroke(1.dp, PtPtTheme.color.primaryNormal)
        } else {
            null
        },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 세트 번호
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        if (isLast) {
                            PtPtTheme.color.primaryNormal
                        } else {
                            PtPtTheme.color.componentStrong
                        },
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "${setDetail.setNumber}",
                    style = PtPtTheme.typography.body02,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }

            // 횟수
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${setDetail.reps}회",
                    style = PtPtTheme.typography.body01,
                    color = PtPtTheme.color.textNormal,
                    fontWeight = FontWeight.Bold,
                )
            }

            // 휴식 시간 (마지막 세트가 아닌 경우)
            if (setDetail.restTime != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = PtPtTheme.icon.pause,
                        contentDescription = null,
                        tint = PtPtTheme.color.textAssist,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = formatDuration(setDetail.restTime),
                        style = PtPtTheme.typography.body02,
                        color = PtPtTheme.color.textAssist,
                    )
                }
            } else {
                Text(
                    text = "완료",
                    style = PtPtTheme.typography.body02,
                    color = PtPtTheme.color.primaryNormal,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

// 하단 액션 버튼
@Composable
fun BottomActionButtons(
    onDiscard: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shadowElevation = 8.dp,
        color = PtPtTheme.color.backgroundNormal,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // 저장 안함 버튼
            OutlinedButton(
                onClick = onDiscard,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PtPtTheme.color.textAssist,
                ),
                border = BorderStroke(1.dp, PtPtTheme.color.textNormal),
            ) {
                Text(
                    text = "저장 안함",
                    style = PtPtTheme.typography.body01,
                    fontWeight = FontWeight.Bold,
                )
            }

            // 저장 버튼
            Button(
                onClick = onSave,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PtPtTheme.color.primaryNormal,
                ),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = PtPtTheme.icon.checkFill,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "저장",
                        style = PtPtTheme.typography.body01,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
            }
        }
    }
}

// 헬퍼 함수
fun formatDuration(seconds: Int): String {
    return when {
        seconds < 60 -> "${seconds}초"
        seconds < 3600 -> {
            val minutes = seconds / 60
            val remainingSeconds = seconds % 60
            if (remainingSeconds == 0) {
                "${minutes}분"
            } else {
                "${minutes}분 ${remainingSeconds}초"
            }
        }

        else -> {
            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            "${hours}시간 ${minutes}분"
        }
    }
}

// 1. 기본 운동 결과 화면
@Preview(name = "Exercise Result - Basic", showSystemUi = true)
@Composable
fun ExerciseResultScreenPreview() {
    PtPtTheme {
        val mockResult = ExerciseResult(
            exerciseName = "팔굽혀펴기",
            startTime = LocalDateTime.now().minusMinutes(35),
            endTime = LocalDateTime.now(),
            totalDuration = 2100,
            totalSets = 4,
            totalReps = 52,
            averageRepsPerSet = 13f,
            totalRestTime = 180,
            averageRestTime = 60f,
            setDetails = listOf(
                SetDetail(1, 15, 60),
                SetDetail(2, 13, 60),
                SetDetail(3, 12, 60),
                SetDetail(4, 12, null),
            ),
        )

        ExerciseResultScreen(
            result = mockResult,
            onSave = {},
            onDiscard = {},
        )
    }
}

// 2. 긴 세트 목록이 있는 경우 (스크롤 테스트)
@Preview(name = "Exercise Result - Many Sets", showSystemUi = true)
@Composable
fun ExerciseResultScreenManySetsPreview() {
    PtPtTheme {
        val mockResult = ExerciseResult(
            exerciseName = "스쿼트",
            startTime = LocalDateTime.now().minusHours(1).minusMinutes(15),
            endTime = LocalDateTime.now(),
            totalDuration = 4500,
            totalSets = 8,
            totalReps = 120,
            averageRepsPerSet = 15f,
            totalRestTime = 420,
            averageRestTime = 60f,
            setDetails = listOf(
                SetDetail(1, 20, 60),
                SetDetail(2, 18, 60),
                SetDetail(3, 16, 60),
                SetDetail(4, 15, 90),
                SetDetail(5, 15, 60),
                SetDetail(6, 14, 60),
                SetDetail(7, 12, 50),
                SetDetail(8, 10, null),
            ),
        )

        ExerciseResultScreen(
            result = mockResult,
            onSave = {},
            onDiscard = {},
        )
    }
}

// 3. 휴식 없는 운동 (Count Up 모드 결과)
@Preview(name = "Exercise Result - No Rest", showSystemUi = true)
@Composable
fun ExerciseResultScreenNoRestPreview() {
    PtPtTheme {
        val mockResult = ExerciseResult(
            exerciseName = "플랭크",
            startTime = LocalDateTime.now().minusMinutes(15),
            endTime = LocalDateTime.now(),
            totalDuration = 900,
            totalSets = 3,
            totalReps = 180,
            averageRepsPerSet = 60f,
            totalRestTime = 0,
            averageRestTime = 0f,
            setDetails = listOf(
                SetDetail(1, 60, null),
                SetDetail(2, 60, null),
                SetDetail(3, 60, null),
            ),
        )

        ExerciseResultScreen(
            result = mockResult,
            onSave = {},
            onDiscard = {},
        )
    }
}
