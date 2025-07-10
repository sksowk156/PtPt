package com.paradise.core.ui.dialog.scope

import PrimaryButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.button.SecondaryButton
import com.paradise.core.ui.radiogroup.RadioGroup
import com.paradise.core.ui.radiogroup.scope.RadioButton

@Stable
interface DialogScope<T> {
    val selectedKey: T?

    fun select(key: T)
}

@Stable
class DialogScopeImpl<T>() : DialogScope<T> {
    private var _selected: T? by mutableStateOf(null)

    override val selectedKey: T? get() = _selected

    override fun select(key: T) {
        _selected = if (_selected == key) null else key
    }
}

@Composable
fun <T> rememberDialogState(): DialogScope<T> = remember { DialogScopeImpl() }

@Composable
fun <T> DialogScope<T>.HorizontalSelectButton(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: (T) -> Unit,
) {
    val currentSelectedKey = this@HorizontalSelectButton.selectedKey

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SecondaryButton(
            modifier = Modifier.weight(1f),
            enabled = currentSelectedKey != null,
            text = "취소",
            onClick = onDismiss,
        )

        PrimaryButton(
            modifier = Modifier.weight(1f),
            enabled = currentSelectedKey != null,
            text = "네",
            onClick = {
                currentSelectedKey?.let {
                    onConfirm(it)
                    onDismiss()
                }
            },
        )
    }
}

@Composable
fun <T> DialogScope<T>.ConfirmRadioGroup(
    initial: T?,
    values: List<T>,
    modifier: Modifier = Modifier,
) {
    var last by rememberSaveable { mutableStateOf<T?>(initial) }

    RadioGroup(modifier = modifier.fillMaxWidth()) {
        values.forEach { value ->
            RadioButton(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                text = value.toString(),
                onSelect = {
                    last = it
                    this@ConfirmRadioGroup.select(value)
                },
            )
        }
    }
}
