package com.paradise.core.designsystem.component.icon

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun PtPtIcon(
    imageVector: ImageVector,
    tint: Color = Color.Unspecified,
    contentDescription: String? = null,
) {
    Icon(
        imageVector = imageVector,
        tint = tint,
        contentDescription = contentDescription,
    )
}
