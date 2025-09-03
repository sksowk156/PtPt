package com.paradise.feature.routine.screen.add

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paradise.core.designsystem.component.icon.PtPtIconButton
import com.paradise.core.designsystem.component.topappbar.MainTopAppBar
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.feature.routine.screen.add.content.analyze.RoutineAnalyzeContent
import com.paradise.feature.routine.screen.add.content.save.RoutineSaveContent
import com.paradise.feature.routine.screen.add.content.select.RoutineSelectContent
import com.paradise.feature.routine.screen.add.model.RoutineAddStep

@Composable
internal fun RoutineAddScreen(onCloseClick: () -> Unit) {
    var currentStep by remember { mutableStateOf(RoutineAddStep.SELECT) }

    RoutineAddScreen(
        modifier = Modifier,
        currentStep = currentStep,
        onStepChange = { currentStep = it },
        onCloseClick = onCloseClick,
        onBackClick = {
            when (currentStep) {
                RoutineAddStep.SELECT -> onCloseClick()
                RoutineAddStep.ANALYZE -> currentStep = RoutineAddStep.SELECT
                RoutineAddStep.SAVE -> currentStep = RoutineAddStep.ANALYZE
            }
        },
    )
}

@Composable
internal fun RoutineAddScreen(
    currentStep: RoutineAddStep,
    onStepChange: (RoutineAddStep) -> Unit,
    onCloseClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PtPtTheme.color.backgroundNormal),
    ) {
        MainTopAppBar(
            leftContent = {
                PtPtIconButton(
                    imageVector = PtPtTheme.icon.back,
                    tint = PtPtTheme.color.textNormal,
                    onClick = onBackClick,
                )
            },
            title = "상체 동작 추가하기",
            rightContent = {
                PtPtIconButton(
                    imageVector = PtPtTheme.icon.cancel,
                    tint = PtPtTheme.color.textNormal,
                    onClick = onCloseClick,
                )
            },
        )

        when (currentStep) {
            RoutineAddStep.SELECT -> {
                RoutineSelectContent(
                    onPoseCountSelected = { count ->
                        onStepChange(RoutineAddStep.ANALYZE)
                    },
                )
            }

            RoutineAddStep.ANALYZE -> {
                RoutineAnalyzeContent(
                    onAnalyzeComplete = {
                        onStepChange(RoutineAddStep.SAVE)
                    },
                )
            }

            RoutineAddStep.SAVE -> {
                RoutineSaveContent(
                    onSaveComplete = {
                        onCloseClick()
                    },
                )
            }
        }
    }
}

@Preview(
    name = "CircuitAdd – Light",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5,
)
@Preview(
    name = "CircuitAdd – Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF000000,
)
@Composable
private fun RoutineAddScreenPreview() {
    PtPtTheme {
        RoutineAddScreen(
            onCloseClick = {},
        )
    }
}
