package com.paradise.core.ui.radiogroup.scope

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Stable
interface RadioGroupScope<T> {
    val selectedOption: T?

    fun selectOption(key: T)
}

class RadioGroupScopeImpl<T>(initial: T?) : RadioGroupScope<T> {
    private var _selected by mutableStateOf(initial)

    override val selectedOption: T? get() = _selected

    override fun selectOption(key: T) {
        _selected = if (_selected == key) null else key
    }
}

@Composable
fun <T> rememberRadioGroupState(initial: T? = null): RadioGroupScope<T> = remember(initial) { RadioGroupScopeImpl(initial) }
