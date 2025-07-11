package com.paradise.core.ui.radiogroup.item

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paradise.core.designsystem.component.button.OutlinedButton
import com.paradise.core.designsystem.component.button.base.BaseButton
import com.paradise.core.ui.radiogroup.scope.RadioGroupScope

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
            this.selectOption(value)
            onSelect(value)
        },
        modifier = modifier,
        size = size,
        enabled = true,
        iconConfig = if (icon != null) BaseButton.IconConfig.Start else BaseButton.IconConfig.None,
        icon = icon,
        isSelected = (this.selectedOption == value),
    )
}
