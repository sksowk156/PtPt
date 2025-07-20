package com.paradise.core.designsystem.component.dialog.scope.item

import PrimaryButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.button.SecondaryButton
import com.paradise.core.designsystem.component.dialog.scope.DialogScope
import com.paradise.core.designsystem.component.radiogroup.RadioGroupLayout
import com.paradise.core.designsystem.component.radiogroup.scope.item.RadioOutlinedButton

@Composable
fun <T> DialogScope<T>.HorizontalSelectButton(
    modifier: Modifier = Modifier,
    dismissText: String,
    confirmText: String,
    onDismiss: () -> Unit,
    onConfirm: (T) -> Unit,
) {
    val currentSelectedKey = this@HorizontalSelectButton.selectedOption

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SecondaryButton(
            modifier = Modifier.weight(1f),
            enabled = currentSelectedKey != null,
            text = dismissText,
            onClick = onDismiss,
        )

        PrimaryButton(
            modifier = Modifier.weight(1f),
            enabled = currentSelectedKey != null,
            text = confirmText,
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
fun DialogScope<Unit>.HorizontalSelectButton(
    modifier: Modifier = Modifier,
    dismissText: String,
    confirmText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SecondaryButton(
            modifier = Modifier.weight(1f),
            text = dismissText,
            onClick = onDismiss,
        )

        PrimaryButton(
            modifier = Modifier.weight(1f),
            text = confirmText,
            onClick = {
                onConfirm()
                onDismiss()
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

    RadioGroupLayout(modifier = modifier.fillMaxWidth()) {
        values.forEach { value ->
            RadioOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                text = value.toString(),
                onSelect = {
                    last = it
                    this@ConfirmRadioGroup.selectOption(value)
                },
            )
        }
    }
}
