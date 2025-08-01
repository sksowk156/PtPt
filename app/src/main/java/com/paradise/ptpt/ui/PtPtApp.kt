package com.paradise.ptpt.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paradise.ptpt.contract.AppState
import com.paradise.ptpt.navigation.AppNavHost
import com.paradise.ptpt.ui.component.BottomNavBar

@Composable
fun PtPtApp(
    appNavState: AppNavState,
    authState: AppState.AuthState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (appNavState.shouldShowBottomBar) {
                BottomNavBar(
                    currentDestination = appNavState.currentDestination,
                    navigateToDestination = {
                        appNavState.navigateToBottomDestination(it)
                    },
                )
            }
        },
    ) { innerPadding ->
        AppNavHost(
            appNavState = appNavState,
            authState = authState,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
