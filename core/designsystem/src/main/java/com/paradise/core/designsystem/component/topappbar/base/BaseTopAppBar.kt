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
        override val dividerColor @Composable get() = themeColors.lineNeutral
    }

    enum class Size(
        val height: Dp,
        val horizontalPadding: Dp,
        val titleTextStyle: (PtPtTypography) -> TextStyle,
    ) {
        Default(
            height = 64.dp,
            horizontalPadding = 20.dp,
            titleTextStyle = { typography -> typography.title02 },
        ),
    }
}

@Composable
fun BaseTopAppBar(
    modifier: Modifier = Modifier,
    style: BaseTopAppBar.Style = BaseTopAppBar.DefaultStyle,
    size: BaseTopAppBar.Size = BaseTopAppBar.Size.Default,
    leftSlot: (@Composable RowScope.() -> Unit)? = null,
    centerSlot: (@Composable () -> Unit)? = null,
    rightSlot: (@Composable RowScope.() -> Unit)? = null,
) {
    Surface(
        modifier = modifier.height(size.height),
        color = style.containerColor,
        contentColor = style.contentColor,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            BaseTopAppBarLayout(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalPadding = size.horizontalPadding,
                leftSlot = leftSlot,
                centerSlot = centerSlot,
                rightSlot = rightSlot,
                defaultTitleTextStyle = size.titleTextStyle(PtPtTheme.typography),
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = style.dividerColor,
            )
        }
    }
}

/**
 * Three-slot Top App Bar layout.
 *
 * ┌─────────────── FULL WIDTH ────────────────┐
 * │  left slot   │    center slot    │  right │
 * └────────────────────────────────────────────┘
 *
 * – left / right의 너비만큼 남은 가로 공간 안에서
 *   center를 '가능한 정중앙'으로 배치하되, 겹치지 않도록 제한(clamp)한다.
 * – TextStyle은 center 슬롯 안에 기본값으로 주입해 둔다.
 */
@Composable
private fun BaseTopAppBarLayout(
    horizontalPadding: Dp,
    defaultTitleTextStyle: TextStyle,
    modifier: Modifier = Modifier,
    leftSlot: (@Composable RowScope.() -> Unit)?,
    centerSlot: (@Composable () -> Unit)?,
    rightSlot: (@Composable RowScope.() -> Unit)?,
) {
    Layout(
        modifier = modifier,
        content = {
            if (leftSlot != null) {
                Row(modifier = Modifier.layoutId("LEFT")) {
                    leftSlot()
                }
            }
            if (centerSlot != null) {
                Box(modifier = Modifier.layoutId("CENTER")) {
                    // title 기본 TextStyle 공급
                    ProvideTextStyle(value = defaultTitleTextStyle) {
                        centerSlot()
                    }
                }
            }
            if (rightSlot != null) {
                Row(modifier = Modifier.layoutId("RIGHT")) {
                    rightSlot()
                }
            }
        },
    ) { measurables, constraints ->
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val leftPlaceable = measurables
            .firstOrNull { it.layoutId == "LEFT" }
            ?.measure(looseConstraints)

        val rightPlaceable = measurables
            .firstOrNull { it.layoutId == "RIGHT" }
            ?.measure(looseConstraints)

        val leftPlaceableWidth = leftPlaceable?.width ?: 0
        val rightPlaceableWidth = rightPlaceable?.width ?: 0
        val maxCenterWidth = (constraints.maxWidth - leftPlaceableWidth - rightPlaceableWidth)
            .coerceAtLeast(0)

        val centerPlaceable = measurables
            .firstOrNull { it.layoutId == "CENTER" }
            ?.measure(looseConstraints.copy(maxWidth = maxCenterWidth))

        val totalBarWidth = constraints.maxWidth
        val totalBarHeight = constraints.maxHeight

        layout(width = totalBarWidth, height = totalBarHeight) {
            fun calculateVerticalCenterPosition(childPlaceable: Placeable?): Int {
                val childHeight = childPlaceable?.height ?: 0
                return (totalBarHeight - childHeight) / 2
            }

            leftPlaceable?.placeRelative(
                x = horizontalPadding.roundToPx(),
                y = calculateVerticalCenterPosition(leftPlaceable),
            )

            rightPlaceable?.placeRelative(
                x = totalBarWidth - horizontalPadding.roundToPx() - rightPlaceable.width,
                y = calculateVerticalCenterPosition(rightPlaceable),
            )

            centerPlaceable?.let { centerPlaceableItem ->
                var centerXPosition = (totalBarWidth - centerPlaceableItem.width) / 2

                val minimumCenterX = leftPlaceableWidth + horizontalPadding.roundToPx()
                val maximumCenterX = totalBarWidth - rightPlaceableWidth -
                    horizontalPadding.roundToPx() - centerPlaceableItem.width

                centerXPosition = centerXPosition.coerceIn(minimumCenterX, maximumCenterX)

                centerPlaceableItem.placeRelative(
                    x = centerXPosition,
                    y = calculateVerticalCenterPosition(centerPlaceableItem),
                )
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
            leftSlot = {
                PtPtIconButton(
                    imageVector = PtPtTheme.icon.back,
                    onClick = {},
                )
            },
            centerSlot = { Text("Title") },
            rightSlot = {
                PtPtIconButton(
                    imageVector = PtPtTheme.icon.plus,
                    onClick = {},
                )
            },
        )
    }
}
