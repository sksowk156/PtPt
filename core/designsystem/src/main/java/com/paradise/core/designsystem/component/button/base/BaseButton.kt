package com.paradise.core.designsystem.component.button.base

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.core.designsystem.theme.schema.PtPtShape
import com.paradise.core.designsystem.theme.schema.PtPtTypography

object BaseButton {
    interface ButtonStyle {
        val backgroundEnabledColor: Color
        val backgroundPressedColor: Color
        val backgroundDisabledColor: Color
        val foregroundEnabledColor: Color // 보통 글자색
        val foregroundPressedColor: Color
        val foregroundDisabledColor: Color
    }

    val primaryStyle: ButtonStyle
        @Composable
        get() = object : ButtonStyle {
            private val themeColors = PtPtTheme.color
            override val backgroundEnabledColor = themeColors.primaryNormal
            override val backgroundPressedColor = themeColors.primaryPressed
            override val backgroundDisabledColor = themeColors.primaryNormal.copy(alpha = 0.4f)
            override val foregroundEnabledColor = themeColors.textBlack
            override val foregroundPressedColor = themeColors.textBlack
            override val foregroundDisabledColor = themeColors.textBlack.copy(alpha = 0.4f)
        }

    val secondaryStyle: ButtonStyle
        @Composable
        get() = object : ButtonStyle {
            private val themeColors = PtPtTheme.color
            override val backgroundEnabledColor = themeColors.secondaryNormal
            override val backgroundPressedColor = themeColors.secondaryPressed
            override val backgroundDisabledColor = themeColors.secondaryNormal.copy(alpha = 0.3f)
            override val foregroundEnabledColor = themeColors.textStrong
            override val foregroundPressedColor = themeColors.textNeutral
            override val foregroundDisabledColor = themeColors.textStrong
        }

    enum class Size(
        val height: Dp,
        val horizontalPadding: Dp,
        val textStyleKey: (PtPtTypography) -> TextStyle,
        val buttonShape: (PtPtShape) -> Shape,
    ) {
        Small(height = 32.dp, horizontalPadding = 14.dp, textStyleKey = { it.caption01 }, buttonShape = { it.s }),
        Medium(height = 40.dp, horizontalPadding = 20.dp, textStyleKey = { it.body03 }, buttonShape = { it.m }),
        Large(height = 48.dp, horizontalPadding = 24.dp, textStyleKey = { it.body01 }, buttonShape = { it.l }),
    }

    enum class IconConfig {
        None,
        Start,
        End,
        Both,
    }

    @Composable
    operator fun invoke(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        style: ButtonStyle = primaryStyle,
        size: Size = Size.Medium,
        enabled: Boolean = true,
        iconConfig: IconConfig = IconConfig.None,
        icon: @Composable (() -> Unit)? = null,
    ) {
        // 1) press 상태
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()

        // 2) background, foreground 색
        val targetBackgroundColor = when {
            !enabled -> style.backgroundDisabledColor
            isPressed -> style.backgroundPressedColor
            else -> style.backgroundEnabledColor
        }

        val animatedBackgroundColor by animateColorAsState(targetBackgroundColor, label = "buttonBackgroundAnimation")
        val currentContentColor = when {
            !enabled -> style.foregroundDisabledColor
            isPressed -> style.foregroundEnabledColor
            else -> style.foregroundEnabledColor
        }

        // 3) 글꼴
        val currentTextStyle = size.textStyleKey(PtPtTheme.typography)

        // 4) Shape
        val buttonShape = size.buttonShape(PtPtTheme.shape)

        // 5) Size
        val iconFixedSize = currentTextStyle.lineHeight.value.dp
        val iconTextSpacing = if (text.isNotBlank() && iconConfig != IconConfig.None && icon != null) 4.dp else 0.dp
        val buttonHeight = size.height
        val horizontalPaddingValue = size.horizontalPadding

        Box(
            modifier = modifier
                .height(buttonHeight)
                .clip(buttonShape)
                .background(animatedBackgroundColor, buttonShape)
                .clickable(
                    enabled = enabled,
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                    onClick = onClick,
                )
                .padding(horizontal = horizontalPaddingValue),
            contentAlignment = Alignment.Center,
        ) {
            CompositionLocalProvider(LocalContentColor provides currentContentColor) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (icon != null && (iconConfig == IconConfig.Start || iconConfig == IconConfig.Both)) {
                        Box(Modifier.size(iconFixedSize)) { icon() }
                        if (text.isNotBlank()) {
                            Spacer(Modifier.width(iconTextSpacing))
                        }
                    }

                    if (text.isNotBlank()) {
                        Text(
                            text = text,
                            style = currentTextStyle,
                            color = currentContentColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    if (icon != null && (iconConfig == IconConfig.End || iconConfig == IconConfig.Both)) {
                        if (text.isNotBlank()) {
                            Spacer(Modifier.width(iconTextSpacing))
                        }
                        Box(Modifier.size(iconFixedSize)) { icon() }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun ImageButtonStylesPreview() {
    PtPtTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Text(
                "icon",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp),
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // 1-1. 아이콘 없는 기본 버튼
                BaseButton(
                    text = "Label",
                    onClick = { /* */ },
                    // 기본 iconPosition = None 이므로 아이콘 생략 가능
                )

                // 1-2. 아이콘이 앞에 오는 버튼
                BaseButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.Start,
                    icon = { Icon(PtPtTheme.icon.none, contentDescription = "Favorite") },
                )

                // 1-3. 아이콘이 뒤에 오는 버튼
                BaseButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.End,
                    icon = { Icon(PtPtTheme.icon.none, contentDescription = "Arrow") },
                )

                // 1-4. 아이콘만 있는 빈 텍스트 없이 버튼 (text="" 로 처리)
                BaseButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.Both,
                    icon = { Icon(PtPtTheme.icon.none, contentDescription = "Info") },
                )
            }

            // 2. Size 섹션
            Text(
                "size",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BaseButton(
                    text = "Large",
                    onClick = { /* */ },
                    size = BaseButton.Size.Large,
                )
                BaseButton(
                    text = "Medium",
                    onClick = { /* */ },
                    size = BaseButton.Size.Medium,
                )
                BaseButton(
                    text = "Small",
                    onClick = { /* */ },
                    size = BaseButton.Size.Small,
                )
            }

            // 3. State 섹션
            Text(
                "state",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // 3-1. 활성 상태: 기본 색
                BaseButton(
                    text = "Active",
                    onClick = { /* */ },
                )
                // 3-2. 활성 상태: 다크 그린 배경 (임시로 래핑하지 않으므로 기본 색 사용)
                // 실제로 컬러를 직접 지정하는 API가 없으므로,
                // 배경 색을 바꾸고 싶다면 CustomizableButton.colors(...) 함수를 수정해야 합니다.
                // 여기서는 예시로 두 번째 버튼을 Disabled 상태와 구별하기 위해 enabled=true 그대로 둡니다.
                BaseButton(
                    text = "Dark Green",
                    onClick = { /* */ },
                )
                // 3-3. 비활성화 상태
                BaseButton(
                    text = "Disabled",
                    onClick = { /* */ },
                    enabled = false,
                )
            }

            Text(
                "icon",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp),
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // 1-1. 아이콘 없는 기본 버튼
                BaseButton(
                    text = "Label",
                    onClick = { /* */ },
                    // 기본 iconPosition = None 이므로 아이콘 생략 가능
                    style = BaseButton.secondaryStyle,
                )

                // 1-2. 아이콘이 앞에 오는 버튼
                BaseButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.Start,
                    icon = { Icon(PtPtTheme.icon.none, contentDescription = "Favorite") },
                    style = BaseButton.secondaryStyle,
                )

                // 1-3. 아이콘이 뒤에 오는 버튼
                BaseButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.End,
                    icon = { Icon(PtPtTheme.icon.none, contentDescription = "Arrow") },
                    style = BaseButton.secondaryStyle,
                )

                // 1-4. 아이콘만 있는 빈 텍스트 없이 버튼 (text="" 로 처리)
                BaseButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.Both,
                    icon = { Icon(PtPtTheme.icon.none, contentDescription = "Info") },
                    style = BaseButton.secondaryStyle,
                )
            }

            // 2. Size 섹션
            Text(
                "size",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BaseButton(
                    text = "Large",
                    onClick = { /* */ },
                    size = BaseButton.Size.Large,
                    style = BaseButton.secondaryStyle,
                )
                BaseButton(
                    text = "Medium",
                    onClick = { /* */ },
                    size = BaseButton.Size.Medium,
                    style = BaseButton.secondaryStyle,
                )
                BaseButton(
                    text = "Small",
                    onClick = { /* */ },
                    size = BaseButton.Size.Small,
                    style = BaseButton.secondaryStyle,
                )
            }

            // 3. State 섹션
            Text(
                "state",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp),
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // 3-1. 활성 상태: 기본 색
                BaseButton(
                    text = "Active",
                    onClick = { /* */ },
                    style = BaseButton.secondaryStyle,
                )
                // 3-2. 활성 상태: 다크 그린 배경 (임시로 래핑하지 않으므로 기본 색 사용)
                // 실제로 컬러를 직접 지정하는 API가 없으므로,
                // 배경 색을 바꾸고 싶다면 CustomizableButton.colors(...) 함수를 수정해야 합니다.
                // 여기서는 예시로 두 번째 버튼을 Disabled 상태와 구별하기 위해 enabled=true 그대로 둡니다.
                BaseButton(
                    text = "Dark Green",
                    onClick = { /* */ },
                    style = BaseButton.secondaryStyle,
                )
                // 3-3. 비활성화 상태
                BaseButton(
                    text = "Disabled",
                    onClick = { /* */ },
                    enabled = false,
                    style = BaseButton.secondaryStyle,
                )
            }
        }
    }
}
