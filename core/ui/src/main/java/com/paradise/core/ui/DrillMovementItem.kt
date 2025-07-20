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
import com.paradise.core.model.DrillMovement

@Composable
fun DrillMovementItem(
    drillMovement: DrillMovement,
    modifier: Modifier = Modifier,
    content: @Composable DrillMovementScope.() -> Unit,
) {
    val scope = remember(drillMovement) {
        object : DrillMovementScope {
            override val drillMovement: DrillMovement = drillMovement
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
                text = scope.drillMovement.name,
                style = PtPtTheme.typography.body04,
                color = PtPtTheme.color.textNormal,
            )

            scope.content()
        }
    }
}

@Stable
interface DrillMovementScope {
    val drillMovement: DrillMovement
}

@Composable
fun DrillMovementScope.StepCount(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = "동작",
            style = PtPtTheme.typography.body04,
            color = PtPtTheme.color.textAlternative,
        )

        Text(
            text = this@StepCount.drillMovement.stepCount.toString(),
            style = PtPtTheme.typography.body03,
            color = PtPtTheme.color.primaryNormal,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DrillMovementItemPreview() {
    PtPtTheme { // 테마 래핑
        val sampleDrillMovement = DrillMovement(
            name = "푸시업",
            stepCount = 2,
        )

        DrillMovementItem(drillMovement = sampleDrillMovement) {
            StepCount(
                modifier = Modifier,
            )
        }
    }
}
