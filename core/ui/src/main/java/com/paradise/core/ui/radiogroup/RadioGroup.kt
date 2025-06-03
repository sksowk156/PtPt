package com.paradise.core.ui.radiogroup

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.core.ui.radiogroup.scope.RadioButton
import com.paradise.core.ui.radiogroup.scope.RadioGroupScope

@Composable
fun <T> RadioGroup(
    modifier: Modifier = Modifier,
    initialSelection: T? = null,
    clearedSelection: T? = null,
    onSelectionChanged: (T?) -> Unit = {},
    content: @Composable RadioGroupScope<T>.() -> Unit,
) {
    var current by rememberSaveable { mutableStateOf<T?>(initialSelection) }

    val scope = remember {
        object : RadioGroupScope<T> {
            override val selectedKey: T? get() = current

            override fun select(key: T) {
                current = if (current == key) null else key
                onSelectionChanged(current ?: clearedSelection)
            }
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        scope.content()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun PrimaryButtonPreview() {
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

            RadioGroup(
                initialSelection = "A",
                clearedSelection = "<none>",
                modifier = Modifier.fillMaxWidth(),
                onSelectionChanged = { selected ->
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
