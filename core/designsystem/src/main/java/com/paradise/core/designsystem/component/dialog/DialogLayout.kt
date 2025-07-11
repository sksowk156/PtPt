package com.paradise.core.designsystem.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.paradise.core.designsystem.component.dialog.item.ConfirmRadioGroup
import com.paradise.core.designsystem.component.dialog.item.HorizontalSelectButton
import com.paradise.core.designsystem.component.dialog.scope.DialogScope
import com.paradise.core.designsystem.component.dialog.scope.rememberDialogState
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
fun <T> DialogLayout(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable DialogScope<T>.() -> Unit,
) {
    val groupState = rememberDialogState<T>()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = PtPtTheme.shape.xl,
            color = PtPtTheme.color.textBlack,
        ) {
            Column(
                modifier = modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                groupState.content()
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DialogLayoutPreview() {
    PtPtTheme {
        DialogLayout(
            onDismiss = {},
            modifier = Modifier,
        ) {
            Row {
                Text("dfd")
            }
            ConfirmRadioGroup(
                initial = null,
                values = listOf("사과", "바나나", "딸기"),
                modifier = Modifier,
            )

            HorizontalSelectButton(
                modifier = Modifier.padding(top = 10.dp),
                dismissText = "취소",
                confirmText = "네",
                onDismiss = {},
                onConfirm = { result ->
                },
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DialogLayoutPreview2() {
    PtPtTheme {
        DialogLayout(
            onDismiss = {},
            modifier = Modifier,
        ) {
            Row {
                Text("dfd")
            }

            HorizontalSelectButton(
                modifier = Modifier.padding(top = 10.dp),
                dismissText = "취소",
                confirmText = "네",
                onDismiss = {},
                onConfirm = {},
            )
        }
    }
}
