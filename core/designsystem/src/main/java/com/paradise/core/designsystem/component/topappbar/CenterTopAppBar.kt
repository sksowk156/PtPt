package com.paradise.core.designsystem.component.topappbar

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paradise.core.designsystem.component.topappbar.base.BaseTopAppBar
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
fun CenterTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    style: BaseTopAppBar.Style = BaseTopAppBar.DefaultStyle,
    size: BaseTopAppBar.Size = BaseTopAppBar.Size.Default,
) = BaseTopAppBar(
    modifier = modifier,
    style = style,
    size = size,
    centerSlot = { Text(text = title) },
)

@Preview(
    name = "CenterTopAppBar",
    showBackground = true,
    backgroundColor = 0xFFEFEFEF,
)
@Composable
private fun Preview_CenterTopAppBar() {
    PtPtTheme {
        CenterTopAppBar(title = "Center-Only Title")
    }
}
