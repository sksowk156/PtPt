package com.paradise.core.ui.radiogroup.scope

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
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
class RadioGroupState<T> internal constructor(initial: T? = null) : RadioGroupScope<T> {
    private var selected: T? by mutableStateOf(initial)

    override val selectedKey: T? get() = selected

    override fun select(key: T) {
        selected = if (selected == key) null else key
    }
}

@Composable
fun <T> rememberRadioGroupState(
    initial: T? = null,
    save: (T?) -> String? = { it?.toString() },
    restore: (String) -> T? = { null },
): RadioGroupState<T> = rememberSaveable(
    saver = Saver(
        save = { save(it.selectedKey) },
        restore = { RadioGroupState(restore(it)) },
    ),
) { RadioGroupState(initial) }

@Composable
fun <T> RadioGroupScope<T>.RadioButton(
    value: T,
    text: String,
    modifier: Modifier = Modifier,
    size: BaseButton.Size = BaseButton.Size.Large,
    icon: (@Composable () -> Unit)? = null,
) {
    OutlinedButton(
        text = text,
        onClick = { this.select(value) },
        modifier = modifier,
        size = size,
        enabled = true,
        iconConfig = if (icon != null) BaseButton.IconConfig.Start else BaseButton.IconConfig.None,
        icon = icon,
        isSelected = (this.selectedKey == value),
    )
}
