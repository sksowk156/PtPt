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
fun StartCenterTopAppBar(
    title: String,
    left: (@Composable RowScope.() -> Unit),
    modifier: Modifier = Modifier,
    style: BaseTopAppBar.Style = BaseTopAppBar.DefaultStyle,
    size: BaseTopAppBar.Size = BaseTopAppBar.Size.Default,
) = BaseTopAppBar(
    modifier = modifier,
    style = style,
    size = size,
    left = left,
    center = { Text(text = title) },
)

// Start + Center
@Preview(showBackground = true, backgroundColor = 0xFFEFEFEF)
@Composable
private fun Preview_StartCenter() {
    PtPtTheme {
        StartCenterTopAppBar(
            title = "Title",
            left = {
                PtPtIconButton(
                    imageVector = PtPtTheme.icon.back,
                    onClick = {},
                )
            },
        )
    }
}
