package com.paradise.feature.routine.screen.add.content.select

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.button.SecondaryButton
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
fun RoutineSelectContent(onPoseCountSelected: (Int) -> Unit) {
    Column {
        Text(
            text = "몇 가지 동작을 등록할까요?",
            color = PtPtTheme.color.textStrong,
            style = PtPtTheme.typography.title01,
            modifier = Modifier
                .padding(top = 43.dp)
                .padding(horizontal = 20.dp),
        )

        Text(
            text = "설명글을 넣습니다.",
            color = PtPtTheme.color.textNormal,
            style = PtPtTheme.typography.body02,
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 20.dp),
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(end = 2.dp),
                text = "예시 보러 가기",
                color = PtPtTheme.color.textNeutral,
                style = PtPtTheme.typography.caption01,
            )

            Icon(
                imageVector = PtPtTheme.icon.front,
                contentDescription = "",
                tint = PtPtTheme.color.textStrong,
                modifier = Modifier.size(10.dp),
            )
        }

        Spacer(Modifier.weight(1f))

        SecondaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = "한 가지",
            onClick = {
                onPoseCountSelected(1)
            },
        )

        Spacer(Modifier.height(12.dp))

        SecondaryButton(
            text = "두 가지",
            onClick = {
                onPoseCountSelected(2)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )

        Spacer(Modifier.height(12.dp))

        SecondaryButton(
            text = "세 가지",
            onClick = {
                onPoseCountSelected(3)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )

        Spacer(Modifier.height(40.dp))
    }
}
