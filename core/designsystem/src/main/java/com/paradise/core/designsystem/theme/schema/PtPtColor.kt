package com.paradise.core.designsystem.theme.schema

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.paradise.core.designsystem.theme.value.green01

@Immutable
data class PtPtColor(
    val primary: Color,
)

internal val ptptLightColorScheme = PtPtColor(
    primary = green01,
)

internal val ptptDarkColorScheme = PtPtColor(
    primary = green01,
)

internal val LocalColor = staticCompositionLocalOf {
    ptptLightColorScheme
}
