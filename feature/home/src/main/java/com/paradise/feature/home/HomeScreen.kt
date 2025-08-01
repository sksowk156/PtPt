package com.paradise.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.R
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
internal fun HomeRoute(
    onCircuitTrainigClick: () -> Unit,
    onTrackingClick: () -> Unit,
) {
    HomeScreen(
        onCircuitTrainigClick = onCircuitTrainigClick,
        onTrackingClick = onTrackingClick,
    )
}

@Composable
internal fun HomeScreen(
    onCircuitTrainigClick: () -> Unit,
    onTrackingClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundWithOverlay()

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            Image(
                modifier = Modifier.padding(start = 24.dp, top = 16.dp),
                painter = painterResource(id = R.drawable.ic_ptpt_logo_small),
                contentDescription = "ptpt logo",
            )

            Spacer(Modifier.height(41.dp))

            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                style = PtPtTheme.typography.title01,
                text = "오늘은 어떤 운동을 하시나요?",
                color = PtPtTheme.color.textStrong,
            )

            Spacer(Modifier.height(8.dp))

            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = "어제 운동량이 평소보다 많아\n너무 무리가 되지 않는 운동을 추천해요 :)",
                color = PtPtTheme.color.textNormal,
                style = PtPtTheme.typography.body02,
            )

            Spacer(Modifier.height(106.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                WorkoutTypeCard(
                    title = "무산소\n운동",
                    subTitle = "N일 전 운동",
                    onClick = onCircuitTrainigClick,
                    modifier = Modifier.weight(1f),
                )

                WorkoutTypeCard(
                    title = "유산소\n운동",
                    subTitle = "N일 전 운동",
                    onClick = onTrackingClick,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun BackgroundWithOverlay() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .blur(8.dp),
            painter = painterResource(id = R.drawable.ic_union),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.55f)),
        )
    }
}

@Composable
private fun WorkoutTypeCard(
    title: String,
    subTitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .aspectRatio(1f),
        onClick = onClick,
        shape = PtPtTheme.shape.l,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                style = PtPtTheme.typography.title01,
                color = PtPtTheme.color.textBlack,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = subTitle,
                style = PtPtTheme.typography.body04,
                color = PtPtTheme.color.textNeutral,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(
    name = "HomeScreen",
    showBackground = true,
    backgroundColor = 0xFF000000,
)
@Composable
private fun HomeScreenPreview() {
    PtPtTheme {
        HomeScreen(
            onCircuitTrainigClick = {},
            onTrackingClick = {},
        )
    }
}
