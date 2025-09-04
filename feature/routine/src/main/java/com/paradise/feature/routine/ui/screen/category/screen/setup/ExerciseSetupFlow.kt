package com.paradise.feature.routine.ui.screen.category.screen.setup

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.core.model.Routine
import com.paradise.feature.routine.model.ExerciseConfig
import com.paradise.feature.routine.model.RepConfig
import com.paradise.feature.routine.model.RestConfig
import com.paradise.feature.routine.model.SetConfig
import com.paradise.feature.routine.ui.screen.category.screen.setup.screen.repetition.RepetitionSetup
import com.paradise.feature.routine.ui.screen.category.screen.setup.screen.rest.RestSetup
import com.paradise.feature.routine.ui.screen.category.screen.setup.screen.set.SetSetup

@Composable
fun ExerciseSetupFlow(
    routine: Routine,
    onStartExercise: (ExerciseConfig) -> Unit,
    onDismiss: () -> Unit,
) {
    var currentStep by remember { mutableStateOf(0) }
    val steps = listOf("횟수", "세트", "휴식")

    // 각 단계별 설정 저장
    var repConfig by remember { mutableStateOf<RepConfig?>(null) }
    var setConfig by remember { mutableStateOf<SetConfig?>(null) }
    var restConfig by remember { mutableStateOf<RestConfig?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PtPtTheme.color.backgroundNormal),
    ) {
        // 상단 Progress Indicator
        StepProgressBar(
            steps = steps,
            currentStep = currentStep,
            modifier = Modifier.padding(20.dp),
        )

        // 각 단계별 컨텐츠
        AnimatedContent(targetState = currentStep) { step ->
            when (step) {
                0 -> RepetitionSetup(
                    initialConfig = repConfig,
                    onNext = { config ->
                        repConfig = config
                        currentStep++
                    },
                )

                1 -> SetSetup(
                    repConfig = repConfig!!,
                    initialConfig = setConfig,
                    onNext = { config ->
                        setConfig = config
                        currentStep++
                    },
                    onBack = { currentStep-- },
                )

                2 -> RestSetup(
                    repConfig = repConfig!!,
                    setConfig = setConfig!!,
                    initialConfig = restConfig,
                    onComplete = { config ->
                        restConfig = config
                        val finalConfig = ExerciseConfig(
                            repConfig = repConfig!!,
                            setConfig = setConfig!!,
                            restConfig = config,
                        )
                        onStartExercise(finalConfig)
                    },
                    onBack = { currentStep-- },
                )
            }
        }
    }
}

@Composable
fun StepProgressBar(
    steps: List<String>,
    currentStep: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            steps.forEachIndexed { index, step ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f),
                ) {
                    // 원형 스텝 인디케이터
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(
                                if (index <= currentStep) {
                                    PtPtTheme.color.primaryNormal
                                } else {
                                    PtPtTheme.color.componentNormal
                                },
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = (index + 1).toString(),
                            style = PtPtTheme.typography.body02,
                            color = if (index <= currentStep) {
                                PtPtTheme.color.primaryNormal
                            } else {
                                PtPtTheme.color.secondaryNormal
                            },
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = step,
                        style = PtPtTheme.typography.caption01,
                        color = if (index <= currentStep) {
                            PtPtTheme.color.textNormal
                        } else {
                            PtPtTheme.color.textAssist
                        },
                    )
                }

                // 연결선
                if (index < steps.lastIndex) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(2.dp)
                            .align(Alignment.CenterVertically)
                            .background(
                                if (index < currentStep) {
                                    PtPtTheme.color.primaryNormal
                                } else {
                                    PtPtTheme.color.componentNormal
                                },
                            ),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun ExerciseSetupFlowPreview() {
    PtPtTheme {
        val mockRoutine = Routine(
            name = "팔굽혀펴기",
            stepCount = 10,
        )

        ExerciseSetupFlow(
            routine = mockRoutine,
            onStartExercise = { },
            onDismiss = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StepProgressBarPreview() {
    PtPtTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // Step 0
            StepProgressBar(
                steps = listOf("횟수", "세트", "휴식"),
                currentStep = 0,
            )

            // Step 1
            StepProgressBar(
                steps = listOf("횟수", "세트", "휴식"),
                currentStep = 1,
            )

            // Step 2
            StepProgressBar(
                steps = listOf("횟수", "세트", "휴식"),
                currentStep = 2,
            )
        }
    }
}
