package com.paradise.feature.routine.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.feature.routine.model.CountMode

@Composable
fun <T> SegmentedButton(
    options: List<T>,
    selected: T,
    onSelectionChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    optionContent: @Composable (T) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(PtPtTheme.shape.m)
            .background(PtPtTheme.color.componentNormal)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        options.forEach { option ->
            val isSelected = option == selected
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(PtPtTheme.shape.s)
                    .background(
                        if (isSelected) {
                            PtPtTheme.color.primaryNormal
                        } else {
                            Color.Transparent
                        },
                    )
                    .clickable { onSelectionChange(option) }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center,
            ) {
                optionContent(option)
            }
        }
    }
}

// 사용 예시를 위한 오버로드
@Composable
fun SegmentedButton(
    options: List<CountMode>,
    selected: CountMode,
    modifier: Modifier = Modifier,
    onSelectionChange: (CountMode) -> Unit,
) {
    SegmentedButton(
        options = options,
        selected = selected,
        onSelectionChange = onSelectionChange,
        modifier = modifier,
    ) { mode ->
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = mode.icon,
                style = PtPtTheme.typography.body03,
                color = if (selected == mode) {
                    PtPtTheme.color.primaryNormal
                } else {
                    PtPtTheme.color.textNormal
                },
            )
            Text(
                text = mode.title,
                style = PtPtTheme.typography.body02,
                color = if (selected == mode) {
                    PtPtTheme.color.primaryNormal
                } else {
                    PtPtTheme.color.textNormal
                },
            )
            Text(
                text = mode.subtitle,
                style = PtPtTheme.typography.caption01,
                color = if (selected == mode) {
                    PtPtTheme.color.primaryNormal.copy(alpha = 0.7f)
                } else {
                    PtPtTheme.color.textAssist
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SegmentedButtonCountModePreview() {
    PtPtTheme {
        var selected by remember {
            mutableStateOf(
                CountMode("↑", "Count Up", "0에서 시작"),
            )
        }

        val options = listOf(
            CountMode("↑", "Count Up", "0에서 시작"),
            CountMode("↓", "Count Down", "목표에서 시작"),
        )

        Column(modifier = Modifier.padding(16.dp)) {
            SegmentedButton(
                options = options,
                selected = selected,
                onSelectionChange = { selected = it },
            ) { mode ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = mode.icon,
                        style = PtPtTheme.typography.body03,
                    )
                    Text(
                        text = mode.title,
                        style = PtPtTheme.typography.body02,
                    )
                    Text(
                        text = mode.subtitle,
                        style = PtPtTheme.typography.caption01,
                    )
                }
            }
        }
    }
}
