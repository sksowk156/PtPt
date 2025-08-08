package com.paradise.core.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.core.model.Routine

@Composable
fun RoutineItem(
    routine: Routine,
    modifier: Modifier = Modifier,
    content: @Composable RoutineScope.() -> Unit,
) {
    val scope = remember(routine) {
        object : RoutineScope {
            override val routine: Routine = routine
        }
    }

    Surface(
        shape = PtPtTheme.shape.l,
        color = PtPtTheme.color.componentNormal,
    ) {
        Row(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = scope.routine.name,
                style = PtPtTheme.typography.body04,
                color = PtPtTheme.color.textNormal,
            )

            scope.content()
        }
    }
}

@Stable
interface RoutineScope {
    val routine: Routine
}

@Composable
fun RoutineScope.StepCount(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = "동작",
            style = PtPtTheme.typography.body04,
            color = PtPtTheme.color.textAlternative,
        )

        Text(
            text = this@StepCount.routine.stepCount.toString(),
            style = PtPtTheme.typography.body03,
            color = PtPtTheme.color.primaryNormal,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovementItemPreview() {
    PtPtTheme { // 테마 래핑
        val sampleRoutine = Routine(
            name = "푸시업",
            stepCount = 2,
        )

        RoutineItem(routine = sampleRoutine) {
            StepCount(modifier = Modifier)
        }
    }
}
