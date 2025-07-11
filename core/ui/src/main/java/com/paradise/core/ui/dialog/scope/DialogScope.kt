package com.paradise.core.ui.dialog.scope

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Stable
interface DialogScope<T> {
    val selectedOption: T?

    fun selectOption(key: T)
}

class DialogScopeImpl<T> : DialogScope<T> {
    private var _selected: T? by mutableStateOf(null)

    override val selectedOption: T? get() = _selected

    override fun selectOption(key: T) {
        _selected = if (_selected == key) null else key
    }
}

@Composable
fun <T> rememberDialogState(): DialogScope<T> = remember { DialogScopeImpl() }
