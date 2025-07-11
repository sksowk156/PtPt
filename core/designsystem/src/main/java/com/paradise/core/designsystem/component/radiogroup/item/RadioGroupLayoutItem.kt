package com.paradise.core.designsystem.component.radiogroup.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.button.OutlinedButton
import com.paradise.core.designsystem.component.button.base.BaseButton
import com.paradise.core.designsystem.component.icon.PtPtIcon
import com.paradise.core.designsystem.component.radiogroup.scope.RadioGroupScope
import com.paradise.core.designsystem.component.radiogroup.scope.rememberRadioGroupState
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
fun <T> RadioGroupScope<T>.RadioOutlinedButton(
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

@Composable
fun <T> RadioGroupScope<T>.RadioCheckButton(
    value: T,
    text: String,
    modifier: Modifier = Modifier,
    onSelect: (T) -> Unit,
) {
    Row(
        modifier = modifier
            .clickable {
                this.selectOption(value)
                onSelect(value)
            }
            .padding(vertical = 13.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = text,
            style = PtPtTheme.typography.body01,
            color = PtPtTheme.color.textStrong,
        )

        if (this@RadioCheckButton.selectedOption == value) {
            PtPtIcon(
                imageVector = PtPtTheme.icon.checkFill,
                tint = PtPtTheme.color.primaryNormal,
            )
        }
    }
}

@Preview(
    name = "RadioOutlinedButton – un-selected",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun RadioOutlinedButtonPreview() {
    PtPtTheme {
        val scope = rememberRadioGroupState<String>()

        Column(modifier = Modifier.background(PtPtTheme.color.textBlack)) {
            with(scope) {
                RadioOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    value = "Apple",
                    text = "사과",
                    onSelect = { /* no-op */ },
                )
            }
        }
    }
}

@Preview(
    name = "RadioOutlinedButton – selected",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun RadioOutlinedButtonSelectedPreview() {
    PtPtTheme {
        val scope = rememberRadioGroupState(initial = "Apple")

        Column(modifier = Modifier.background(PtPtTheme.color.textBlack)) {
            with(scope) {
                RadioOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    value = "Apple",
                    text = "사과",
                    onSelect = { /* no-op */ },
                )
            }
        }
    }
}

@Preview(
    name = "RadioCheckButton – un-selected",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun RadioCheckButtonPreview() {
    PtPtTheme {
        val scope = rememberRadioGroupState<String>()

        Column(modifier = Modifier.background(PtPtTheme.color.textBlack)) {
            with(scope) {
                RadioCheckButton(
                    modifier = Modifier.fillMaxWidth(),
                    value = "Apple",
                    text = "사과",
                    onSelect = { /* no-op */ },
                )
            }
        }
    }
}

@Preview(
    name = "RadioCheckButton – selected",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun RadioCheckButtonSelectedPreview() {
    PtPtTheme {
        val scope = rememberRadioGroupState(initial = "Apple")

        Column(modifier = Modifier.background(PtPtTheme.color.textBlack)) {
            with(scope) {
                RadioCheckButton(
                    modifier = Modifier.fillMaxWidth(),
                    value = "Apple",
                    text = "사과",
                    onSelect = { /* no-op */ },
                )
            }
        }
    }
}
