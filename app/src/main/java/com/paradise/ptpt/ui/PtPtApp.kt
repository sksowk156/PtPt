package com.paradise.ptpt.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paradise.ptpt.navigation.AppNavHost
import com.paradise.ptpt.ui.component.BottomNavBar

@Composable
fun PtPtApp(
    appNavState: AppNavState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavBar(
                currentDestination = appNavState.currentDestination,
                navigateToDestination = {
                    appNavState.navigateToDestination(it)
                },
            )
        },
    ) { innerPadding ->
        AppNavHost(
            appNavState = appNavState,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
