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
    left: (@Composable RowScope.() -> Unit),
    right: (@Composable RowScope.() -> Unit),
    modifier: Modifier = Modifier,
    style: BaseTopAppBar.Style = BaseTopAppBar.DefaultStyle,
    size: BaseTopAppBar.Size = BaseTopAppBar.Size.Default,
) = BaseTopAppBar(
    modifier = modifier,
    style = style,
    size = size,
    left = left,
    center = { Text(text = title) },
    right = right,
)

@Preview(showBackground = true, backgroundColor = 0xFFEFEFEF)
@Composable
private fun Preview_StartCenterEnd_LCR() {
    PtPtTheme {
        StartCenterEndTopAppBar(
            title = "Title",
            left = {
                PtPtIconButton(
                    imageVector = PtPtTheme.icon.back,
                    onClick = {},
                )
            },
            right = {
                PtPtIconButton(
                    imageVector = PtPtTheme.icon.back,
                    onClick = {},
                )
            },
        )
    }
}
