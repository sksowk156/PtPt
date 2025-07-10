package com.paradise.core.ui.radiogroup.scope

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.paradise.core.designsystem.component.button.OutlinedButton
import com.paradise.core.designsystem.component.button.base.BaseButton

@Stable
interface RadioGroupScope<T> {
    val selectedKey: T?

    fun select(key: T)
}

@Stable
class RadioGroupScopeImpl<T>(initial: T?) : RadioGroupScope<T> {
    private var _selected by mutableStateOf(initial)

    override val selectedKey: T? get() = _selected

    override fun select(key: T) {
        _selected = if (_selected == key) null else key
    }
}

@Composable
fun <T> rememberRadioGroupState(initial: T? = null): RadioGroupScope<T> = remember(initial) { RadioGroupScopeImpl(initial) }

@Composable
fun <T> RadioGroupScope<T>.RadioButton(
    value: T,
    text: String,
    modifier: Modifier = Modifier,
    onSelect: (T) -> Unit,
    size: BaseButton.Size = BaseButton.Size.Large,
    icon: (@Composable () -> Unit)? = null,
) {
    OutlinedButton(
        text = text,
        onClick = {
            this.select(value)
            onSelect(value)
        },
        modifier = modifier,
        size = size,
        enabled = true,
        iconConfig = if (icon != null) BaseButton.IconConfig.Start else BaseButton.IconConfig.None,
        icon = icon,
        isSelected = (this.selectedKey == value),
    )
}
