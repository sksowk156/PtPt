package com.paradise.ptpt.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.paradise.core.designsystem.R
import com.paradise.core.ui.route.Route
import kotlin.reflect.KClass

enum class BottomNavDestination(
    @DrawableRes val selectdImageVector: Int,
    @DrawableRes val unSelectedImageVector: Int,
    @StringRes val title: Int,
    val contentDescription: String,
    val route: KClass<*>,
) {
    HOME(
        selectdImageVector = R.drawable.ic_home_on,
        unSelectedImageVector = R.drawable.ic_home_off,
        contentDescription = "홈 화면",
        title = R.string.bottom_nav_destination_title_home,
        route = Route.HomeBase::class,
    ),
    RECORD(
        selectdImageVector = R.drawable.ic_calendar_on,
        unSelectedImageVector = R.drawable.ic_calendar_off,
        contentDescription = "기록",
        title = R.string.bottom_nav_destination_title_record,
        route = Route.Record::class,
    ),
    MY(
        selectdImageVector = R.drawable.ic_my_on,
        unSelectedImageVector = R.drawable.ic_my_off,
        contentDescription = "내 정보",
        title = R.string.bottom_nav_destination_title_my,
        route = Route.My::class,
    ),
}
