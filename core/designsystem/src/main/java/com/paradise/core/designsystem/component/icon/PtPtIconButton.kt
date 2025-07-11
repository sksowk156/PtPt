package com.paradise.core.designsystem.component.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun PtPtIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
    contentDescription: String? = null,
    enabled: Boolean = true,
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.size(width = imageVector.defaultWidth, height = imageVector.defaultHeight),
    ) {
        PtPtIcon(
            imageVector = imageVector,
            tint = tint,
            contentDescription = contentDescription,
        )
    }
}
