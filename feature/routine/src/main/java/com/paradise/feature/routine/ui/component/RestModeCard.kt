package com.paradise.feature.routine.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
fun RestModeCard(
    selected: Boolean,
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        border = if (selected) {
            BorderStroke(2.dp, PtPtTheme.color.primaryNormal)
        } else {
            BorderStroke(1.dp, PtPtTheme.color.textBlack)
        },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                PtPtTheme.color.primaryNormal.copy(alpha = 0.1f)
            } else {
                PtPtTheme.color.componentNormal
            },
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (selected) {
                    PtPtTheme.color.primaryNormal
                } else {
                    PtPtTheme.color.textAssist
                },
                modifier = Modifier.size(24.dp),
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = PtPtTheme.typography.body01,
                    color = PtPtTheme.color.textNormal,
                )
                Text(
                    text = description,
                    style = PtPtTheme.typography.caption01,
                    color = PtPtTheme.color.textAssist,
                )
            }

            if (selected) {
                Icon(
                    imageVector = PtPtTheme.icon.checkFill,
                    contentDescription = null,
                    tint = PtPtTheme.color.primaryNormal,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RestModeCardPreview() {
    PtPtTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            RestModeCard(
                selected = true,
                icon = PtPtTheme.icon.calendarOn,
                title = "수동 휴식",
                description = "준비되면 직접 다음 세트 시작",
                onClick = { },
            )

            RestModeCard(
                selected = false,
                icon = PtPtTheme.icon.time,
                title = "자동 휴식",
                description = "설정한 시간 후 자동 시작",
                onClick = { },
            )
        }
    }
}
