package com.paradise.feature.auth

import androidx.compose.runtime.Composable
import com.paradise.feature.auth.component.AuthScreen
import com.paradise.feature.auth.component.rememberAuthScreenStateHolder

@Composable
internal fun AuthRoute() {
    val screenStateHolder = rememberAuthScreenStateHolder()

    AuthScreen(screenStateHolder = screenStateHolder)
}
