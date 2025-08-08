package com.paradise.feature.routine.screen.add.save

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun RoutineSaveScreen(
    onCloseClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    RoutineSaveScreen(
        onCloseClick = onCloseClick,
        onBackClick = onBackClick,
        modifier = Modifier,
    )
}

@Composable
internal fun RoutineSaveScreen(
    onCloseClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(Color.Red)
            .fillMaxSize(),
    ) {
    }
}
