package com.paradise.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Stable
interface TopAppBarScope

class DefaultTopAppBarScope : TopAppBarScope

@Composable
fun TopAppBar(content: @Composable TopAppBarScope.() -> Unit) {
    val scope = remember { DefaultTopAppBarScope() }

    Row(modifier = Modifier.fillMaxWidth()) {
        scope.content()
    }
}
