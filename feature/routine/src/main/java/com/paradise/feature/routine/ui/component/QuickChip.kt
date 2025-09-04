package com.paradise.feature.routine.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
fun QuickChip(
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .clip(PtPtTheme.shape.s)
            .background(
                if (selected) {
                    PtPtTheme.color.primaryNormal
                } else {
                    PtPtTheme.color.componentNormal
                },
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = PtPtTheme.typography.body02,
            color = if (selected) {
                PtPtTheme.color.primaryNormal
            } else {
                PtPtTheme.color.textNormal
            },
        )
    }
}

@Composable
fun SettingSummaryChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(PtPtTheme.shape.s)
            .background(PtPtTheme.color.componentNormal)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = PtPtTheme.icon.checkFill,
            contentDescription = null,
            tint = PtPtTheme.color.primaryNormal,
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = PtPtTheme.typography.body02,
            color = PtPtTheme.color.textNormal,
        )
    }
}

@Composable
fun TimeChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit = {},
) {
    QuickChip(text = text, selected = selected, onClick = onClick)
}

@Preview(showBackground = true)
@Composable
fun QuickChipPreview() {
    PtPtTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            QuickChip("10", selected = false) { }
            QuickChip("15", selected = true) { }
            QuickChip("20", selected = false) { }
            QuickChip("30", selected = false) { }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingSummaryChipPreview() {
    PtPtTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SettingSummaryChip("15회 ↑")
            SettingSummaryChip("3세트 ↑")
            SettingSummaryChip("60초 자동 휴식")
        }
    }
}
