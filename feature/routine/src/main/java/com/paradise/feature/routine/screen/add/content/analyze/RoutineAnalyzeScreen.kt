package com.paradise.feature.routine.screen.add.content.analyze

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun RoutineAnalyzeScreen(
    onCloseClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    RoutineAnalyzeScreen(
        onCloseClick = onCloseClick,
        onBackClick = onBackClick,
        modifier = Modifier,
    )
}

@Composable
internal fun RoutineAnalyzeScreen(
    onCloseClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(Color.Blue)
            .fillMaxSize(),
    ) {
    }
}
