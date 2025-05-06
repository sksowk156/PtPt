package com.paradise.core.designsystem.theme.schema

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.paradise.core.designsystem.R

@Immutable
class PtPtIcon {
    val addSmall: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_add_small)

    val back: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_back)

    val calendarOff: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_calendar_off)

    val calendarOn: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_calendar_on)

    val cancel: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_cancel)

    val checkFill: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_check_fill)

    val checkLine: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_check_line)

    val down: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_down)

    val filter: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_filter)

    val front: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_front)

    val homeOff: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_home_off)

    val homeOn: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_home_on)

    val location: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_location)

    val minus: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_minus)

    val minusCircle: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_minus_circle)

    val myOn: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_my_on)

    val myOff: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_my_off)

    val none: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_none)

    val pause: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_pause)

    val plus: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_plus)

    val plusCircle: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_plus_circle)

    val refresh: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_refresh)

    val replay: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_replay)

    val time: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_time)

    val union: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_union)
}

internal val LocalIcon = staticCompositionLocalOf {
    PtPtIcon()
}
