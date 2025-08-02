package com.paradise.core.designsystem.component.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.paradise.core.designsystem.theme.PtPtTheme

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
        Icon(
            imageVector = imageVector,
            tint = tint,
            contentDescription = contentDescription,
        )
    }
}

@Preview(
    name = "PtPtIconButton",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5,
)
@Composable
private fun PtPtIconButtonPreview() {
    PtPtTheme {
        PtPtIconButton(
            imageVector = PtPtTheme.icon.plus,
            onClick = {},
        )
    }
}
