package com.paradise.ptpt.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paradise.ptpt.navigation.AppNavHost
import com.paradise.ptpt.ui.component.BottomNavBar

@Composable
fun PtPtApp(
    bottomNavState: AppNavState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavBar(
                currentDestination = bottomNavState.currentDestination,
                navigateToDestination = {
                    bottomNavState.navigateToDestination(it)
                },
            )
        },
    ) { innerPadding ->
        AppNavHost(
            appNavState = bottomNavState,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
