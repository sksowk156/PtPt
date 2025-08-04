package com.paradise.feature.circuit.screen.add

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.button.SecondaryButton
import com.paradise.core.designsystem.component.icon.PtPtIconButton
import com.paradise.core.designsystem.component.topappbar.MainTopAppBar
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
internal fun CircuitAddScreen(
    onBackClick: () -> Unit,
    onMovementCountSelected: (Int) -> Unit,
) {
    CircuitAddScreen(
        onBackClick = onBackClick,
        onMovementCountSelected = onMovementCountSelected,
    )
}

@Composable
internal fun CircuitAddScreen(
    onBackClick: () -> Unit,
    onMovementCountSelected: (Int) -> Unit,
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
                    onClick = {
                    },
                )
            },
        )

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
                onMovementCountSelected(1)
            },
        )

        Spacer(Modifier.height(12.dp))

        SecondaryButton(
            text = "두 가지",
            onClick = {
                onMovementCountSelected(2)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )

        Spacer(Modifier.height(12.dp))

        SecondaryButton(
            text = "세 가지",
            onClick = {
                onMovementCountSelected(3)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )

        Spacer(Modifier.height(40.dp))
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
private fun CircuitAddScreenPreview() {
    PtPtTheme {
        CircuitAddScreen(
            onBackClick = {},
            onMovementCountSelected = { /* no-op */ },
        )
    }
}
