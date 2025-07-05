package com.paradise.core.designsystem.component.topappbar.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.icon.PtPtIconButton
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.core.designsystem.theme.schema.PtPtTypography

object BaseTopAppBar {
    interface Style {
        val containerColor: Color @Composable get
        val contentColor: Color @Composable get
        val dividerColor: Color @Composable get
    }

    object DefaultStyle : Style {
        private val themeColors @Composable get() = PtPtTheme.color

        override val containerColor @Composable get() = themeColors.backgroundNormal
        override val contentColor @Composable get() = themeColors.textStrong
        override val dividerColor @Composable get() = themeColors.textNormal
    }

    enum class Size(
        val height: Dp,
        val horizontalPadding: Dp,
        val titleTextStyle: (PtPtTypography) -> TextStyle,
    ) {
        Default(height = 64.dp, horizontalPadding = 20.dp, titleTextStyle = { it.title02 }),
    }

    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        style: Style = DefaultStyle,
        size: Size = Size.Default,
        left: (@Composable RowScope.() -> Unit)? = null,
        center: (@Composable () -> Unit)? = null,
        right: (@Composable RowScope.() -> Unit)? = null,
    ) {
        Surface(
            modifier = modifier.height(size.height),
            color = style.containerColor,
            contentColor = style.contentColor,
        ) {
            Column(Modifier.fillMaxSize()) {
                // ① TopBarLayout 을 weight 로 채우고
                TopBarLayout(
                    modifier = Modifier
                        .weight(1f) // 남는 전체 높이 차지
                        .fillMaxWidth(),
                    horizontalPadding = size.horizontalPadding,
                    left = left,
                    center = center,
                    right = right,
                    defaultTitleStyle = size.titleTextStyle(PtPtTheme.typography),
                )
                // ② Divider 는 Column 의 마지막 Item → 항상 맨 아래
                HorizontalDivider(thickness = 1.dp, color = style.dividerColor)
            }
        }
    }

    /**
     * Three–slot Top-App-Bar layout.
     *
     * ┌─────────────── FULL WIDTH ────────────────┐
     * │  left slot   │    center slot    │  right │
     * └────────────────────────────────────────────┘
     *
     * – left / right 의 너비만큼 남은 가로 공간 안에서
     *   center 를 ‘가능한 정중앙’ 으로 배치하되, 겹치지 않도록 제한(clamp)한다.
     * – TextStyle 은 center 슬롯 안에 기본값으로 주입해 둔다.
     */
    @Composable
    private fun TopBarLayout(
        horizontalPadding: Dp,
        defaultTitleStyle: TextStyle,
        modifier: Modifier = Modifier,
        left: (@Composable RowScope.() -> Unit)?,
        center: (@Composable () -> Unit)?,
        right: (@Composable RowScope.() -> Unit)?,
    ) {
        Layout(
            modifier = modifier,
            content = {
                // ① 세 슬롯을 layoutId 로 구분해 삽입
                if (left != null) Row(Modifier.layoutId("LEFT")) { left() }
                if (center != null) {
                    Box(Modifier.layoutId("CENTER")) {
                        // title 기본 TextStyle 공급
                        ProvideTextStyle(defaultTitleStyle) { center() }
                    }
                }
                if (right != null) Row(Modifier.layoutId("RIGHT")) { right() }
            },
        ) { measurables, constraints ->

            // ────────── 1. 각 자식 측정 ──────────

            // minWidth / minHeight 를 0 으로 하여 “제한 없는” 측정 사양
            val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

            // 왼쪽·오른쪽 먼저 폭을 확정
            val leftPlaceable = measurables.firstOrNull { it.layoutId == "LEFT" }?.measure(looseConstraints)
            val rightPlaceable = measurables.firstOrNull { it.layoutId == "RIGHT" }?.measure(looseConstraints)

            // 가운데는 ‘남는 가로 공간’(barWidth - 좌우 폭) 안에서만 측정
            val maxCenterWidth = (constraints.maxWidth - (leftPlaceable?.width ?: 0) - (rightPlaceable?.width ?: 0))
                .coerceAtLeast(0)

            val centerPlaceable = measurables.firstOrNull { it.layoutId == "CENTER" }
                ?.measure(looseConstraints.copy(maxWidth = maxCenterWidth))

            // ────────── 2. 레이아웃 크기 결정 ──────────
            val barWidth = constraints.maxWidth
            val barHeight = constraints.maxHeight

            layout(barWidth, barHeight) {
                // 세로 가운데 정렬 헬퍼
                fun verticalCenter(child: Placeable?) = (barHeight - (child?.height ?: 0)) / 2

                // ─ Left slot ─
                leftPlaceable?.placeRelative(
                    x = horizontalPadding.roundToPx(),
                    y = verticalCenter(leftPlaceable),
                )

                // ─ Right slot ─
                rightPlaceable?.placeRelative(
                    x = barWidth - horizontalPadding.roundToPx() - rightPlaceable.width,
                    y = verticalCenter(rightPlaceable),
                )

                // ─ Center slot ─
                centerPlaceable?.let { center ->
                    // 1) 이상적인 중앙 위치
                    var centerX = (barWidth - center.width) / 2

                    // 2) 좌·우 겹침 방지를 위한 최소 / 최대 X
                    val minX = (leftPlaceable?.width ?: 0) + horizontalPadding.roundToPx()
                    val maxX = barWidth - (rightPlaceable?.width ?: 0) -
                        horizontalPadding.roundToPx() - center.width

                    // 3) clamp
                    centerX = centerX.coerceIn(minX, maxX)

                    center.placeRelative(
                        x = centerX,
                        y = verticalCenter(center),
                    )
                }
            }
        }
    }
}

@Preview(
    name = "BaseTopAppBar – L/C/R",
    showBackground = true,
    backgroundColor = 0xFFEFEFEF,
)
@Composable
private fun Preview_BaseTopAppBar_LCR() {
    PtPtTheme {
        BaseTopAppBar(
            left = {
                PtPtIconButton(
                    imageVector = PtPtTheme.icon.back,
                    onClick = {},
                )
            },
            center = { Text("Title") },
            right = {
                PtPtIconButton(
                    imageVector = PtPtTheme.icon.plus,
                    onClick = {},
                )
            },
        )
    }
}
