package com.paradise.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paradise.core.designsystem.component.icon.PtPtIconButton
import com.paradise.core.designsystem.component.topappbar.base.BaseTopAppBar
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
fun MainTopAppBar(
    modifier: Modifier = Modifier,
    leftContent: (@Composable RowScope.() -> Unit)? = null,
    centerContent: (@Composable () -> Unit)? = null,
    rightContent: (@Composable RowScope.() -> Unit)? = null,
    title: String? = null,
    style: BaseTopAppBar.Style = BaseTopAppBar.DefaultStyle,
    size: BaseTopAppBar.Size = BaseTopAppBar.Size.Default,
) {
    BaseTopAppBar(
        modifier = modifier,
        style = style,
        size = size,
        leftSlot = leftContent,
        centerSlot = centerContent ?: {
            Text(title ?: "")
        },
        rightSlot = rightContent,
    )
}

@Preview(
    name = "MainTopAppBar – examples",
    showBackground = true,
    backgroundColor = 0xFF101012,
)
@Composable
private fun MainTopAppBarPreview() {
    PtPtTheme {
        Column {
            // ───────── 1) Start + Center ─────────
            MainTopAppBar(
                title = "무산소 운동",
                leftContent = {
                    PtPtIconButton(
                        imageVector = PtPtTheme.icon.back,
                        tint = PtPtTheme.color.textNormal,
                        onClick = {},
                    )
                },
            )

            // ───────── 2) Center only ─────────
            MainTopAppBar(
                title = "무산소 운동",
            )

            // ───────── 3) Start + Center + End ─────────
            MainTopAppBar(
                title = "무산소 운동",
                leftContent = {
                    PtPtIconButton(
                        imageVector = PtPtTheme.icon.back,
                        tint = PtPtTheme.color.textNormal,
                        onClick = {},
                    )
                },
                rightContent = {
                    PtPtIconButton(
                        imageVector = PtPtTheme.icon.addSmall,
                        onClick = {},
                    )
                },
            )
        }
    }
}
