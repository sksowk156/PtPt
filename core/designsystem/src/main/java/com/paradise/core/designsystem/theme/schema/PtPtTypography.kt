package com.paradise.core.designsystem.theme.schema

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.paradise.core.designsystem.theme.value.*

@Immutable
data class PtPtTypography(
    val title01: TextStyle = TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 24.sp,
        lineHeight = 24.sp * 1.4,
        letterSpacing = (-0.003).em,
    ),
    val title02: TextStyle = TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 18.sp,
        lineHeight = 18.sp * 1.4,
        letterSpacing = (-0.003).em,
    ),
    val body01: TextStyle = TextStyle(
        fontFamily = pretendardBold,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.4,
        letterSpacing = (-0.003).em,
    ),
    val body02: TextStyle = TextStyle(
        fontFamily = pretendardMedium,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.5,
        letterSpacing = (-0.003).em,
    ),
    val body03: TextStyle = TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.4,
        letterSpacing = (-0.003).em,
    ),
    val body04: TextStyle = TextStyle(
        fontFamily = pretendardMedium,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.5,
        letterSpacing = (-0.003).em,
    ),
    val caption01: TextStyle = TextStyle(
        fontFamily = pretendardSemiBold,
        fontSize = 12.sp,
        lineHeight = 12.sp * 1.4,
        letterSpacing = (-0.003).em,
    ),
)

internal val LocalTypography = staticCompositionLocalOf {
    PtPtTypography()
}
