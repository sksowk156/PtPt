package com.paradise.core.designsystem.theme.schema

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.paradise.core.designsystem.R

@Immutable
class PtPtIcon {
    val direction: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_launcher_background)
}

internal val LocalIcon = staticCompositionLocalOf {
    PtPtIcon()
}
