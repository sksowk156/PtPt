package com.paradise.core.ui.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.core.ui.dialog.scope.ConfirmRadioGroup
import com.paradise.core.ui.dialog.scope.DialogScope
import com.paradise.core.ui.dialog.scope.HorizontalSelectButton
import com.paradise.core.ui.dialog.scope.rememberDialogState

// @Composable
// fun BaseDialog(
//    title: String,
//    onConfirm: () -> Unit,
//    onDismiss: () -> Unit,
// ) {
//    Dialog(onDismissRequest = onDismiss) {
//        Surface(
//            shape = PtPtTheme.shape.xl,
//            color = PtPtTheme.color.textBlack,
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(24.dp)
//                    .fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//            ) {
//                Text(
//                    text = title,
//                    style = PtPtTheme.typography.title02,
//                    color = PtPtTheme.color.textStrong,
//                )
//
//                Spacer(modifier = Modifier.height(24.dp))
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                ) {
//                    SecondaryButton(
//                        modifier = Modifier.weight(1f),
//                        text = "취소",
//                        onClick = onDismiss,
//                    )
//
//                    PrimaryButton(
//                        modifier = Modifier.weight(1f),
//                        text = "네",
//                        onClick = onConfirm,
//                    )
//                }
//            }
//        }
//    }
// }

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
fun BaseDialogPreview() {
    PtPtTheme {
//        BaseDialog(
//            title = "로그아웃 하시겠습니까?",
//            onConfirm = { },
//            onDismiss = { },
//        )

        DialogLayout(
            onDismiss = {},
            modifier = Modifier,
        ) {
            ConfirmRadioGroup(
                initial = null,
                values = listOf("사과", "바나나", "딸기"),
                modifier = Modifier,
            )

            HorizontalSelectButton(
                modifier = Modifier.padding(top = 10.dp),
                onDismiss = {},
                onConfirm = { result ->
                },
            )
        }
    }
}
