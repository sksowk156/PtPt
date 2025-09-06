package com.paradise.feature.routine.ui.screen.category.screen.workout

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paradise.core.designsystem.component.icon.PtPtIconButton
import com.paradise.core.designsystem.component.topappbar.MainTopAppBar
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.feature.routine.model.ExerciseConfig
import com.paradise.feature.routine.model.RepConfig
import com.paradise.feature.routine.model.RestConfig
import com.paradise.feature.routine.model.RestMode
import com.paradise.feature.routine.model.SetConfig
import com.paradise.feature.routine.ui.screen.category.screen.rest.CircularRestTimer
import kotlinx.coroutines.delay

@Composable
fun ExerciseScreen(
    routineName: String,
    exerciseConfig: ExerciseConfig,
    onPause: () -> Unit,
    onStop: () -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentReps by remember {
        mutableStateOf(
            if (exerciseConfig.repConfig.isCountUp) {
                0
            } else {
                exerciseConfig.repConfig.count
            },
        )
    }
    var currentSet by remember { mutableStateOf(1) }
    var isResting by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    var restTimeLeft by remember { mutableStateOf(exerciseConfig.restConfig.seconds ?: 0) }
    val totalRestTime = exerciseConfig.restConfig.seconds ?: 60

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        // 카메라 프리뷰 - 전체 화면
        CameraPreviewPlaceholder(
            modifier = Modifier.fillMaxSize(),
//            showCameraIcon = !isResting, // 휴식 중에는 아이콘 숨김
        )

        // 상단 앱바
        ExerciseTopAppBar(
            routineName = routineName,
            currentSet = currentSet,
            totalSets = if (exerciseConfig.setConfig.isCountUp) {
                null
            } else {
                exerciseConfig.setConfig.count
            },
            onStop = onStop,
            modifier = Modifier.align(Alignment.TopCenter),
        )

        // 휴식 타이머 - 중앙 원형 디스플레이
        if (isResting) {
            CircularRestTimer(
                timeLeft = restTimeLeft,
                totalTime = totalRestTime,
                onSkip = {
                    isResting = false
                    restTimeLeft = totalRestTime
                },
                modifier = Modifier.align(Alignment.Center),
            )
        }

        // 운동 정보 카드 - 좌측 하단
        if (!isResting) {
            ExerciseInfoCard(
                currentReps = currentReps,
                targetReps = if (exerciseConfig.repConfig.isCountUp) {
                    null
                } else {
                    exerciseConfig.repConfig.count
                },
                isCountUp = exerciseConfig.repConfig.isCountUp,
                isPaused = isPaused,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
                    .navigationBarsPadding(),
            )
        }

        // 컨트롤 버튼 - 우측 하단
        ExerciseFloatingControls(
            isPaused = isPaused,
            isResting = isResting,
            onPauseResume = { isPaused = !isPaused },
            onNextSet = {
                if (exerciseConfig.setConfig.isCountUp ||
                    currentSet < (exerciseConfig.setConfig.count ?: 0)
                ) {
                    currentSet++
                    isResting = exerciseConfig.restConfig.mode == RestMode.AUTO
                    restTimeLeft = totalRestTime
                }
            },
            onComplete = onComplete,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .navigationBarsPadding(),
        )
    }
}

@Composable
fun CameraPreviewPlaceholder(modifier: Modifier) {
    Box(
        modifier = modifier
            .background(Color.Black)
            .fillMaxSize(),
    )
}

// MainTopAppBar를 사용한 상단바
@Composable
fun ExerciseTopAppBar(
    routineName: String,
    currentSet: Int,
    totalSets: Int?,
    onStop: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MainTopAppBar(
        modifier = modifier,
        leftContent = {
            // 세트 정보
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(PtPtTheme.color.primaryNormal.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = currentSet.toString(),
                        style = PtPtTheme.typography.body02,
                        color = PtPtTheme.color.primaryNormal,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Column {
                    Text(
                        text = "SET",
                        style = PtPtTheme.typography.caption01,
                        color = PtPtTheme.color.textAssist,
                    )
                    Text(
                        text = if (totalSets != null) "/ $totalSets" else "∞",
                        style = PtPtTheme.typography.body02,
                        color = PtPtTheme.color.textNormal,
                    )
                }
            }
        },
        centerContent = {
            // 운동 이름
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = routineName,
                    style = PtPtTheme.typography.body01,
                    color = PtPtTheme.color.textNormal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        },
        rightContent = {
            // 종료 버튼
            PtPtIconButton(
                imageVector = PtPtTheme.icon.cancel,
                tint = PtPtTheme.color.textNormal,
                onClick = onStop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(PtPtTheme.color.componentNormal),
            )
        },
    )
}

// 운동 정보 카드 - 간소화된 버전
@Composable
fun ExerciseInfoCard(
    currentReps: Int,
    targetReps: Int?,
    isCountUp: Boolean,
    isPaused: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.7f),
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (isPaused) {
                PtPtTheme.color.assistRed.copy(alpha = 0.5f)
            } else {
                Color.White.copy(alpha = 0.2f)
            },
        ),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            // 횟수 라벨
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "REPS",
                    style = PtPtTheme.typography.caption01,
                    color = Color.White.copy(alpha = 0.6f),
                )

                if (isCountUp) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = PtPtTheme.icon.plus,
                        contentDescription = null,
                        tint = PtPtTheme.color.primaryNormal,
                        modifier = Modifier.size(12.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 횟수 표시
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = currentReps.toString(),
                    style = TextStyle(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isPaused) PtPtTheme.color.assistRed else Color.White,
                    ),
                )

                if (!isCountUp && targetReps != null) {
                    Text(
                        text = "/ $targetReps",
                        style = PtPtTheme.typography.body02,
                        color = Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                }
            }

            // 진행률 바 (Count Down일 때만)
            if (!isCountUp && targetReps != null && targetReps > 0) {
                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = (targetReps - currentReps).toFloat() / targetReps,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = PtPtTheme.color.primaryNormal,
                    trackColor = Color.White.copy(alpha = 0.2f),
                )
            }

            // 일시정지 상태 표시
            if (isPaused) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = PtPtTheme.icon.pause,
                        contentDescription = null,
                        tint = PtPtTheme.color.assistRed,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "일시정지",
                        style = PtPtTheme.typography.caption01,
                        color = PtPtTheme.color.assistRed,
                    )
                }
            }
        }
    }
}

@Composable
fun ExerciseFloatingControls(
    isPaused: Boolean,
    isResting: Boolean,
    onPauseResume: () -> Unit,
    onNextSet: () -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.End,
    ) {
        // 완료 버튼 - 항상 표시
        FloatingActionButton(
            onClick = onComplete,
            modifier = Modifier.size(48.dp),
            containerColor = PtPtTheme.color.primaryNormal.copy(alpha = 0.9f),
            contentColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp,
            ),
        ) {
            Icon(
                imageVector = PtPtTheme.icon.checkFill,
                contentDescription = "완료",
                modifier = Modifier.size(24.dp),
            )
        }

        // 다음 세트 버튼 - 휴식 중이 아닐 때만 표시
        AnimatedVisibility(
            visible = !isResting,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
        ) {
            FloatingActionButton(
                onClick = onNextSet,
                modifier = Modifier.size(56.dp),
                containerColor = PtPtTheme.color.primaryNormal,
                contentColor = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 10.dp,
                ),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        imageVector = PtPtTheme.icon.front,
                        contentDescription = "다음 세트",
                        modifier = Modifier.size(20.dp),
                    )
                    Text(
                        text = "NEXT",
                        style = PtPtTheme.typography.caption01.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        color = Color.White,
                    )
                }
            }
        }

        // 일시정지/재개 버튼 - 메인 액션
        ExtendedFloatingActionButton(
            onClick = onPauseResume,
            modifier = Modifier.height(64.dp),
            containerColor = if (isPaused) {
                PtPtTheme.color.primaryNormal
            } else {
                Color.Black.copy(alpha = 0.6f)
            },
            contentColor = Color.White,
            shape = RoundedCornerShape(32.dp),
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 8.dp,
                pressedElevation = 12.dp,
            ),
        ) {
            AnimatedContent(
                targetState = isPaused,
                transitionSpec = {
                    fadeIn().togetherWith(fadeOut())
                },
            ) { paused ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = if (paused) PtPtTheme.icon.plus else PtPtTheme.icon.pause,
                        contentDescription = if (paused) "재개" else "일시정지",
                        modifier = Modifier.size(28.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (paused) "재개" else "일시정지",
                        style = PtPtTheme.typography.body02,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

// 1. 기본 운동 화면 - Count Down 모드
@Preview(
    name = "Exercise - Count Down Active",
    showSystemUi = true,
    device = Devices.PIXEL_4,
)
@Composable
fun ExerciseScreenCountDownActivePreview() {
    PtPtTheme {
        ExerciseScreen(
            routineName = "팔굽혀펴기",
            exerciseConfig = ExerciseConfig(
                repConfig = RepConfig(count = 15, isCountUp = false),
                setConfig = SetConfig(count = 3, isCountUp = false),
                restConfig = RestConfig(mode = RestMode.AUTO, seconds = 60),
            ),
            onPause = {},
            onStop = {},
            onComplete = {},
        )
    }
}

// 2. Count Up 모드 - 목표 없이 진행
@Preview(
    name = "Exercise - Count Up Mode",
    showSystemUi = true,
    device = Devices.PIXEL_4,
)
@Composable
fun ExerciseScreenCountUpPreview() {
    PtPtTheme {
        ExerciseScreen(
            routineName = "플랭크",
            exerciseConfig = ExerciseConfig(
                repConfig = RepConfig(count = 0, isCountUp = true),
                setConfig = SetConfig(count = 0, isCountUp = true),
                restConfig = RestConfig(mode = RestMode.MANUAL, seconds = null),
            ),
            onPause = {},
            onStop = {},
            onComplete = {},
        )
    }
}

// 3. 휴식 중 화면 시뮬레이션
@Preview(
    name = "Exercise - Rest Mode",
    showSystemUi = true,
    device = Devices.PIXEL_4,
)
@Composable
fun ExerciseScreenRestModePreview() {
    PtPtTheme {
        var isResting by remember { mutableStateOf(true) }
        var restTimeLeft by remember { mutableStateOf(45) }

        LaunchedEffect(restTimeLeft) {
            if (isResting && restTimeLeft > 0) {
                delay(1000)
                restTimeLeft--
            }
        }

        Box(Modifier.fillMaxSize()) {
            CameraPreviewPlaceholder(
                modifier = Modifier.fillMaxSize(),
            )

            ExerciseTopAppBar(
                routineName = "스쿼트",
                currentSet = 2,
                totalSets = 4,
                onStop = {},
                modifier = Modifier.align(Alignment.TopCenter),
            )

            if (isResting) {
                CircularRestTimer(
                    timeLeft = restTimeLeft,
                    totalTime = 60,
                    onSkip = {
                        isResting = false
                        restTimeLeft = 60
                    },
                    modifier = Modifier.align(Alignment.Center),
                )
            } else {
                ExerciseInfoCard(
                    currentReps = 0,
                    targetReps = 15,
                    isCountUp = false,
                    isPaused = false,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp)
                        .navigationBarsPadding(),
                )
            }

            ExerciseFloatingControls(
                isPaused = false,
                isResting = isResting,
                onPauseResume = {},
                onNextSet = { isResting = true },
                onComplete = {},
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
                    .navigationBarsPadding(),
            )
        }
    }
}

// 4. 일시정지 상태
@Preview(
    name = "Exercise - Paused State",
    showSystemUi = true,
)
@Composable
fun ExerciseScreenPausedPreview() {
    PtPtTheme {
        Box(Modifier.fillMaxSize()) {
            CameraPreviewPlaceholder(
                modifier = Modifier.fillMaxSize(),
            )

            ExerciseTopAppBar(
                routineName = "데드리프트",
                currentSet = 3,
                totalSets = 5,
                onStop = {},
                modifier = Modifier.align(Alignment.TopCenter),
            )

            ExerciseInfoCard(
                currentReps = 8,
                targetReps = 12,
                isCountUp = false,
                isPaused = true,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
                    .navigationBarsPadding(),
            )

            ExerciseFloatingControls(
                isPaused = true,
                isResting = false,
                onPauseResume = {},
                onNextSet = {},
                onComplete = {},
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
                    .navigationBarsPadding(),
            )
        }
    }
}

// 5. Interactive Preview - 운동 진행 시뮬레이션
@Preview(
    name = "Exercise - Interactive",
    showSystemUi = true,
    device = Devices.PIXEL_4,
)
@Composable
fun ExerciseScreenInteractivePreview() {
    PtPtTheme {
        var currentReps by remember { mutableStateOf(0) }
        var currentSet by remember { mutableStateOf(1) }
        var isResting by remember { mutableStateOf(false) }
        var isPaused by remember { mutableStateOf(false) }
        var restTimeLeft by remember { mutableStateOf(60) }
        val targetReps = 10
        val totalSets = 3

        // 자동 카운트 시뮬레이션
        LaunchedEffect(isPaused, isResting, currentReps) {
            if (!isPaused && !isResting && currentReps < targetReps) {
                delay(2000)
                currentReps++
            }
        }

        // 휴식 타이머 시뮬레이션
        LaunchedEffect(isResting, restTimeLeft) {
            if (isResting && restTimeLeft > 0) {
                delay(1000)
                restTimeLeft--
                if (restTimeLeft == 0) {
                    isResting = false
                    currentReps = 0
                    restTimeLeft = 60
                }
            }
        }

        Box(Modifier.fillMaxSize()) {
            CameraPreviewPlaceholder(
                modifier = Modifier.fillMaxSize(),
            )

            ExerciseTopAppBar(
                routineName = "벤치프레스",
                currentSet = currentSet,
                totalSets = totalSets,
                onStop = {},
                modifier = Modifier.align(Alignment.TopCenter),
            )

            if (isResting) {
                CircularRestTimer(
                    timeLeft = restTimeLeft,
                    totalTime = 60,
                    onSkip = {
                        isResting = false
                        currentReps = 0
                        restTimeLeft = 60
                    },
                    modifier = Modifier.align(Alignment.Center),
                )
            } else {
                ExerciseInfoCard(
                    currentReps = currentReps,
                    targetReps = targetReps,
                    isCountUp = false,
                    isPaused = isPaused,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp)
                        .navigationBarsPadding(),
                )
            }

            ExerciseFloatingControls(
                isPaused = isPaused,
                isResting = isResting,
                onPauseResume = { isPaused = !isPaused },
                onNextSet = {
                    if (currentReps >= targetReps && currentSet < totalSets) {
                        currentSet++
                        isResting = true
                        currentReps = 0
                    }
                },
                onComplete = {},
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
                    .navigationBarsPadding(),
            )
        }
    }
}

// 6. FloatingControls 단독 Preview
@Preview(name = "Floating Controls - All States")
@Composable
fun ExerciseFloatingControlsPreview() {
    PtPtTheme {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            // 운동 중
            ExerciseFloatingControls(
                isPaused = false,
                isResting = false,
                onPauseResume = {},
                onNextSet = {},
                onComplete = {},
            )

            // 일시정지
            ExerciseFloatingControls(
                isPaused = true,
                isResting = false,
                onPauseResume = {},
                onNextSet = {},
                onComplete = {},
            )

            // 휴식 중
            ExerciseFloatingControls(
                isPaused = false,
                isResting = true,
                onPauseResume = {},
                onNextSet = {},
                onComplete = {},
            )
        }
    }
}

// 7. 태블릿 Preview
@Preview(
    name = "Exercise - Tablet",
    showSystemUi = true,
    device = "spec:width=1280dp,height=800dp,dpi=240",
)
@Composable
fun ExerciseScreenTabletPreview() {
    PtPtTheme {
        ExerciseScreen(
            routineName = "풀업",
            exerciseConfig = ExerciseConfig(
                repConfig = RepConfig(count = 10, isCountUp = false),
                setConfig = SetConfig(count = 4, isCountUp = false),
                restConfig = RestConfig(mode = RestMode.AUTO, seconds = 90),
            ),
            onPause = {},
            onStop = {},
            onComplete = {},
        )
    }
}

// 8. 가로 모드 Preview
@Preview(
    name = "Exercise - Landscape",
    showSystemUi = true,
    device = "spec:orientation=landscape,width=891dp,height=411dp",
)
@Composable
fun ExerciseScreenLandscapePreview() {
    PtPtTheme {
        ExerciseScreen(
            routineName = "런지",
            exerciseConfig = ExerciseConfig(
                repConfig = RepConfig(count = 20, isCountUp = false),
                setConfig = SetConfig(count = 3, isCountUp = false),
                restConfig = RestConfig(mode = RestMode.MANUAL, seconds = null),
            ),
            onPause = {},
            onStop = {},
            onComplete = {},
        )
    }
}
