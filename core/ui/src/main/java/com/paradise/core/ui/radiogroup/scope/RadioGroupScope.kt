package com.paradise.core.ui.radiogroup.scope

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import com.paradise.core.designsystem.component.button.OutlinedButton
import com.paradise.core.designsystem.component.button.base.BaseButton

@Stable
interface RadioGroupScope<T> {
    val selectedKey: T?

    fun select(key: T)
}

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
