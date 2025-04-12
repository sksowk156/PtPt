package com.paradise.core.designsystem.theme.schema

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import com.paradise.core.designsystem.theme.value.round10
import com.paradise.core.designsystem.theme.value.round12
import com.paradise.core.designsystem.theme.value.round24
import com.paradise.core.designsystem.theme.value.round8

@Immutable
data class PtPtShape(
    val s: Shape = round8,
    val m: Shape = round10,
    val l: Shape = round12,
    val xl: Shape = round24,
)

internal val LocalShape = staticCompositionLocalOf {
    PtPtShape()
}
