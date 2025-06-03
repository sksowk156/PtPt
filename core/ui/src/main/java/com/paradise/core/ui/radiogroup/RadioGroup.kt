package com.paradise.core.ui.radiogroup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.core.ui.radiogroup.scope.RadioButton
import com.paradise.core.ui.radiogroup.scope.RadioGroupScope
import com.paradise.core.ui.radiogroup.scope.RadioGroupState
import com.paradise.core.ui.radiogroup.scope.rememberRadioGroupState

@Composable
fun <T> RadioGroup(
    state: RadioGroupState<T>,
    modifier: Modifier = Modifier,
    onChanged: (T?) -> Unit = {},
    content: @Composable RadioGroupScope<T>.() -> Unit,
) {
    LaunchedEffect(state.selectedKey) { onChanged(state.selectedKey) }

    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        state.content()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun PrimaryButtonPreview() {
    PtPtTheme {
        var last by rememberSaveable { mutableStateOf<String?>("A") }

        val radioState = rememberRadioGroupState(initial = "A")

        Column(
            modifier = Modifier
                .background(PtPtTheme.color.textBlack)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = "마지막 선택: $last", color = Color.White)

            RadioGroup(
                state = radioState,
                modifier = Modifier.fillMaxWidth(),
                onChanged = { selected ->
                    last = selected
                },
            ) {
                RadioButton(value = "A", text = "Apple", modifier = Modifier.fillMaxWidth())
                RadioButton(value = "B", text = "Banana", modifier = Modifier.fillMaxWidth())
                RadioButton(value = "C", text = "Cherry", modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
