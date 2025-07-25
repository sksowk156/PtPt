package com.paradise.ptpt.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.ptpt.navigation.BottomNavDestination
import kotlin.reflect.KClass

@Composable
fun BottomNavBar(
    currentDestination: NavDestination?,
    navigateToDestination: (BottomNavDestination) -> Unit,
) {
    NavigationBar(
        modifier = Modifier
            .navigationBarsPadding()
            .height(60.dp),
        containerColor = PtPtTheme.color.textBlack,
        tonalElevation = 0.dp,
    ) {
        BottomNavDestination.entries.forEach { destination ->
            val isSelected = currentDestination.isRouteInHierarchy(destination.route)

            val menuIconRes = if (isSelected) {
                destination.selectdImageVector
            } else {
                destination.unSelectedImageVector
            }

            val textColor = if (isSelected) {
                PtPtTheme.color.primaryNormal
            } else {
                PtPtTheme.color.textAssist
            }

            NavigationBarItem(
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(menuIconRes),
                            contentDescription = destination.contentDescription,
                            tint = Color.Unspecified,
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            style = PtPtTheme.typography.caption01,
                            color = textColor,
                            text = stringResource(destination.title),
                        )
                    }
                },
                alwaysShowLabel = false,
                selected = isSelected,
                onClick = { navigateToDestination(destination) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Unspecified,
                    unselectedIconColor = Color.Unspecified,
                    disabledIconColor = Color.Unspecified,
                    selectedTextColor = Color.Unspecified,
                    unselectedTextColor = Color.Unspecified,
                    disabledTextColor = Color.Unspecified,
                    indicatorColor = Color.Transparent,
                ),
                interactionSource = remember { MutableInteractionSource() },
            )
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) = this?.hierarchy?.any {
    it.hasRoute(route)
} ?: false
