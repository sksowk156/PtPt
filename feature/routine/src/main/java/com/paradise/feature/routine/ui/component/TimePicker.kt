package com.paradise.feature.routine.ui.component

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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
fun TimePicker(
    minutes: Int,
    seconds: Int,
    onTimeChange: (minutes: Int, seconds: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 분 선택
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "분",
                style = PtPtTheme.typography.caption01,
                color = PtPtTheme.color.textAssist,
            )
            Spacer(modifier = Modifier.height(8.dp))

            NumberWheelPicker(
                value = minutes,
                onValueChange = { onTimeChange(it, seconds) },
                range = 0..5,
                modifier = Modifier.width(80.dp),
            )
        }

        Text(
            ":",
            style = PtPtTheme.typography.body02,
            color = PtPtTheme.color.textNormal,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        // 초 선택
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "초",
                style = PtPtTheme.typography.caption01,
                color = PtPtTheme.color.textAssist,
            )
            Spacer(modifier = Modifier.height(8.dp))

            NumberWheelPicker(
                value = seconds,
                onValueChange = { onTimeChange(minutes, it) },
                range = 0..59,
                // 5초 단위로 증감
                step = 5,
                modifier = Modifier.width(80.dp),
            )
        }
    }
}

@Composable
fun NumberWheelPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange,
    step: Int = 1,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 증가 버튼
        IconButton(
            onClick = {
                val newValue = value + step
                if (newValue <= range.last) {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier.size(32.dp),
        ) {
            Icon(
                imageVector = PtPtTheme.icon.plus,
                contentDescription = "증가",
                tint = if (value < range.last) {
                    PtPtTheme.color.textNormal
                } else {
                    PtPtTheme.color.textAssist
                },
            )
        }

        // 숫자 표시 영역
        Box(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .background(
                    PtPtTheme.color.componentNormal,
                    shape = PtPtTheme.shape.s,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = value.toString().padStart(2, '0'),
                style = PtPtTheme.typography.body01,
                color = PtPtTheme.color.textNormal,
            )
        }

        // 감소 버튼
        IconButton(
            onClick = {
                val newValue = value - step
                if (newValue >= range.first) {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier.size(32.dp),
        ) {
            Icon(
                imageVector = PtPtTheme.icon.down,
                contentDescription = "감소",
                tint = if (value > range.first) {
                    PtPtTheme.color.textNormal
                } else {
                    PtPtTheme.color.textAssist
                },
            )
        }
    }
}
