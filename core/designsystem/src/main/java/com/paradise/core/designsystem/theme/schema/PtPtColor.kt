package com.paradise.core.designsystem.theme.schema

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.paradise.core.designsystem.theme.value.blue10
import com.paradise.core.designsystem.theme.value.green100
import com.paradise.core.designsystem.theme.value.green60
import com.paradise.core.designsystem.theme.value.green70
import com.paradise.core.designsystem.theme.value.grey10
import com.paradise.core.designsystem.theme.value.grey20
import com.paradise.core.designsystem.theme.value.grey25
import com.paradise.core.designsystem.theme.value.grey35
import com.paradise.core.designsystem.theme.value.grey5
import com.paradise.core.designsystem.theme.value.grey50
import com.paradise.core.designsystem.theme.value.grey55
import com.paradise.core.designsystem.theme.value.grey70
import com.paradise.core.designsystem.theme.value.grey80
import com.paradise.core.designsystem.theme.value.red10
import com.paradise.core.designsystem.theme.value.red20

@Immutable
data class PtPtColor(
    val primaryNormal: Color,
    val primaryPressed: Color,
    val statusPositive: Color,
    val statusDestructive: Color,
    val assistBlue: Color,
    val assistRed: Color,
    val textStrong: Color,
    val textNormal: Color,
    val textNeutral: Color,
    val textAlternative: Color,
    val textAssist: Color,
    val textBlack: Color,
    val textDisable: Color,
    val backgroundNormal: Color,
    val backgroundElevated: Color,
    val secondaryNormal: Color,
    val secondaryPressed: Color,
    val componentNormal: Color,
    val componentStrong: Color,
    val componentAlternative: Color,
    val lineNeutral: Color,
    val lineBrightLine: Color,
)

internal val ptptLightColorScheme = PtPtColor(
    primaryNormal = green70,
    primaryPressed = green60,
    statusPositive = green100,
    statusDestructive = red10,
    assistBlue = blue10,
    assistRed = red20,
    textStrong = grey80,
    textNormal = grey70,
    textNeutral = grey55,
    textAlternative = grey50,
    textAssist = grey35,
    textBlack = grey5,
    textDisable = grey25,
    backgroundNormal = grey5,
    backgroundElevated = grey10,
    secondaryNormal = grey20,
    secondaryPressed = grey10,
    componentNormal = grey10,
    componentStrong = grey20,
    componentAlternative = grey10,
    lineNeutral = grey10,
    lineBrightLine = grey70,
)

internal val ptptDarkColorScheme = PtPtColor(
    primaryNormal = green70,
    primaryPressed = green60,
    statusPositive = green100,
    statusDestructive = red10,
    assistBlue = blue10,
    assistRed = red20,
    textStrong = grey80,
    textNormal = grey70,
    textNeutral = grey55,
    textAlternative = grey50,
    textAssist = grey35,
    textBlack = grey5,
    textDisable = grey25,
    backgroundNormal = grey5,
    backgroundElevated = grey10,
    secondaryNormal = grey20,
    secondaryPressed = grey10,
    componentNormal = grey10,
    componentStrong = grey20,
    componentAlternative = grey10,
    lineNeutral = grey10,
    lineBrightLine = grey70,
)

internal val LocalColor = staticCompositionLocalOf {
    ptptLightColorScheme
}
