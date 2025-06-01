package com.paradise.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.paradise.core.designsystem.theme.schema.LocalColor
import com.paradise.core.designsystem.theme.schema.LocalIcon
import com.paradise.core.designsystem.theme.schema.LocalShape
import com.paradise.core.designsystem.theme.schema.LocalTypography
import com.paradise.core.designsystem.theme.schema.PtPtColor
import com.paradise.core.designsystem.theme.schema.PtPtIcon
import com.paradise.core.designsystem.theme.schema.PtPtShape
import com.paradise.core.designsystem.theme.schema.PtPtTypography
import com.paradise.core.designsystem.theme.schema.ptptDarkColorScheme
import com.paradise.core.designsystem.theme.schema.ptptLightColorScheme

@Composable
fun PtPtTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: PtPtTypography = PtPtTheme.typography,
    shape: PtPtShape = PtPtTheme.shape,
    icon: PtPtIcon = PtPtTheme.icon,
    content: @Composable () -> Unit,
) {
    val color = if (darkTheme) ptptLightColorScheme else ptptDarkColorScheme

    CompositionLocalProvider(
        LocalTypography provides typography,
        LocalShape provides shape,
        LocalIcon provides icon,
        LocalColor provides color,
    ) {
        content()
    }
}

object PtPtTheme {
    val color: PtPtColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColor.current

    val typography: PtPtTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shape: PtPtShape
        @Composable
        @ReadOnlyComposable
        get() = LocalShape.current

    val icon: PtPtIcon
        @Composable
        @ReadOnlyComposable
        get() = LocalIcon.current
}
