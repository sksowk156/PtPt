package com.paradise.core.designsystem.component.radiogroup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.radiogroup.item.RadioOutlinedButton
import com.paradise.core.designsystem.component.radiogroup.scope.RadioGroupScope
import com.paradise.core.designsystem.component.radiogroup.scope.rememberRadioGroupState
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
fun <T> RadioGroupLayout(
    modifier: Modifier = Modifier,
    initial: T? = null,
    content: @Composable RadioGroupScope<T>.() -> Unit,
) {
    val groupState = rememberRadioGroupState(initial)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        groupState.content()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
private fun PrimaryButtonPreview() {
    PtPtTheme {
        var last by rememberSaveable { mutableStateOf<String?>("A") }

        Column(
            modifier = Modifier
                .background(PtPtTheme.color.textBlack)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = "마지막 선택: $last", color = Color.White)

            RadioGroupLayout(modifier = Modifier.fillMaxWidth()) {
                RadioOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    value = "A",
                    text = "Apple",
                    onSelect = {
                        last = it
                    },
                )
                RadioOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    value = "B",
                    text = "Banana",
                    onSelect = {
                        last = it
                    },
                )
                RadioOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    value = "C",
                    text = "Cherry",
                    onSelect = {
                        last = it
                    },
                )
            }
        }
    }
}
