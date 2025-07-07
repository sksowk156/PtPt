package com.paradise.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paradise.core.designsystem.component.icon.PtPtIconButton
import com.paradise.core.designsystem.component.topappbar.base.BaseTopAppBar
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
fun StartCenterEndTopAppBar(
    title: String,
    leftSlot: (@Composable RowScope.() -> Unit),
    rightSlot: (@Composable RowScope.() -> Unit),
    modifier: Modifier = Modifier,
    style: BaseTopAppBar.Style = BaseTopAppBar.DefaultStyle,
    size: BaseTopAppBar.Size = BaseTopAppBar.Size.Default,
) = BaseTopAppBar(
    modifier = modifier,
    style = style,
    size = size,
    leftSlot = leftSlot,
    centerSlot = { Text(text = title) },
    rightSlot = rightSlot,
)

@Preview(showBackground = true, backgroundColor = 0xFFEFEFEF)
@Composable
private fun Preview_StartCenterEnd_LCR() {
    PtPtTheme {
        StartCenterEndTopAppBar(
            title = "Title",
            leftSlot = {
                PtPtIconButton(
                    imageVector = PtPtTheme.icon.back,
                    onClick = {},
                )
            },
            rightSlot = {
                PtPtIconButton(
                    imageVector = PtPtTheme.icon.back,
                    onClick = {},
                )
            },
        )
    }
}
