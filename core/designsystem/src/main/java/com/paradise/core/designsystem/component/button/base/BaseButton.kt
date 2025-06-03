package com.paradise.core.designsystem.component.button.base

import PrimaryButton
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.button.OutlinedButton
import com.paradise.core.designsystem.component.button.SecondaryButton
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.core.designsystem.theme.schema.PtPtShape
import com.paradise.core.designsystem.theme.schema.PtPtTypography

object BaseButton {
    interface ButtonStyle {
        val backgroundEnabledColor: Color @Composable get
        val backgroundPressedColor: Color @Composable get
        val backgroundDisabledColor: Color @Composable get

        val foregroundEnabledColor: Color @Composable get
        val foregroundPressedColor: Color @Composable get
        val foregroundDisabledColor: Color @Composable get

        val borderEnabledColor: Color @Composable get
        val borderPressedColor: Color @Composable get
        val borderDisabledColor: Color @Composable get

        val borderWidth: Dp
    }

    object PrimaryStyle : ButtonStyle {
        private val themeColors @Composable get() = PtPtTheme.color

        override val backgroundEnabledColor @Composable get() = themeColors.primaryNormal
        override val backgroundPressedColor @Composable get() = themeColors.primaryPressed
        override val backgroundDisabledColor @Composable get() = themeColors.primaryNormal.copy(alpha = .4f)

        override val foregroundEnabledColor @Composable get() = themeColors.textBlack
        override val foregroundPressedColor @Composable get() = themeColors.textBlack
        override val foregroundDisabledColor @Composable get() = themeColors.textBlack.copy(alpha = .4f)

        override val borderEnabledColor @Composable get() = Color.Transparent
        override val borderPressedColor @Composable get() = Color.Transparent
        override val borderDisabledColor @Composable get() = Color.Transparent
        override val borderWidth: Dp = 0.dp
    }

    object SecondaryStyle : ButtonStyle {
        private val themeColors @Composable get() = PtPtTheme.color

        override val backgroundEnabledColor @Composable get() = themeColors.secondaryNormal
        override val backgroundPressedColor @Composable get() = themeColors.secondaryPressed
        override val backgroundDisabledColor @Composable get() = themeColors.secondaryNormal.copy(alpha = .3f)

        override val foregroundEnabledColor @Composable get() = themeColors.textStrong
        override val foregroundPressedColor @Composable get() = themeColors.textNeutral
        override val foregroundDisabledColor @Composable get() = themeColors.textStrong.copy(alpha = .4f)

        override val borderEnabledColor @Composable get() = Color.Transparent
        override val borderPressedColor @Composable get() = Color.Transparent
        override val borderDisabledColor @Composable get() = Color.Transparent
        override val borderWidth: Dp = 0.dp
    }

    object OutlineStyle : ButtonStyle {
        private val themeColors @Composable get() = PtPtTheme.color

        override val backgroundEnabledColor @Composable get() = Color.Transparent
        override val backgroundPressedColor @Composable get() = Color.Transparent
        override val backgroundDisabledColor @Composable get() = Color.Transparent

        override val foregroundEnabledColor @Composable get() = themeColors.textNeutral
        override val foregroundPressedColor @Composable get() = themeColors.primaryPressed
        override val foregroundDisabledColor @Composable get() = themeColors.textNeutral.copy(alpha = .4f)

        override val borderEnabledColor @Composable get() = themeColors.textAssist
        override val borderPressedColor @Composable get() = themeColors.primaryNormal
        override val borderDisabledColor @Composable get() = themeColors.textAssist.copy(alpha = .4f)
        override val borderWidth: Dp = 1.dp
    }

    enum class Size(
        val height: Dp,
        val horizontalPadding: Dp,
        val textStyleKey: (PtPtTypography) -> TextStyle,
        val shapeKey: (PtPtShape) -> Shape,
    ) {
        Small(32.dp, 14.dp, { it.caption01 }, { it.s }),
        Medium(40.dp, 20.dp, { it.body03 }, { it.m }),
        Large(48.dp, 24.dp, { it.body01 }, { it.l }),
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
        style: ButtonStyle = PrimaryStyle,
        size: Size = Size.Medium,
        enabled: Boolean = true,
        isSelected: Boolean = false,
        iconConfig: IconConfig = IconConfig.None,
        icon: @Composable (() -> Unit)? = null,
    ) {
        // 1. 상호작용(Pressed) 추적
        val interactionSource = remember { MutableInteractionSource() }
        val pressed by interactionSource.collectIsPressedAsState()

        // 1-1. “활성된 눌린 상태” 판단
        //    • 일반(Primary/Secondary) 스타일은 실제 누를 때 pressed를 사용
        //    • OutlinedStyle 은 라디오처럼 isSelected 값으로 “항상 눌린” 모양을 유지
        val isActivePressed = when (style) {
            OutlineStyle -> isSelected
            else -> pressed
        }

        // 2. 상태별 색 결정
        val bgTarget = when {
            isActivePressed -> style.backgroundPressedColor
            !enabled -> style.backgroundDisabledColor
            else -> style.backgroundEnabledColor
        }
        val fgTarget = when {
            isActivePressed -> style.foregroundPressedColor
            !enabled -> style.foregroundDisabledColor
            else -> style.foregroundEnabledColor
        }
        val strokeTarget = when {
            isActivePressed -> style.borderPressedColor
            !enabled -> style.borderDisabledColor
            else -> style.borderEnabledColor
        }

        // 3. 애니메이션
        val animatedBg by animateColorAsState(bgTarget, label = "buttonBackground")
        val animatedStroke by animateColorAsState(strokeTarget, label = "buttonBorder")

        // 4. 텍스트 스타일·Shape·아이콘 크기 계산
        val txtStyle = size.textStyleKey(PtPtTheme.typography)
        val shape = size.shapeKey(PtPtTheme.shape)
        val iconSize = txtStyle.lineHeight.value.dp
        val spacing = if (icon != null && iconConfig != IconConfig.None && text.isNotBlank()) {
            4.dp
        } else {
            0.dp
        }

        // 5. 실제 레이아웃
        Surface(
            modifier = modifier.height(size.height),
            shape = shape,
            color = animatedBg,
            border = if (style.borderWidth > 0.dp) {
                BorderStroke(style.borderWidth, animatedStroke)
            } else {
                null
            },
        ) {
            Row(
                modifier = Modifier
                    .clickable(
                        enabled = enabled,
                        interactionSource = interactionSource,
                        indication = LocalIndication.current,
                        onClick = onClick,
                    )
                    .padding(horizontal = size.horizontalPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                CompositionLocalProvider(LocalContentColor provides fgTarget) {
                    if (icon != null && (iconConfig == IconConfig.Start || iconConfig == IconConfig.Both)) {
                        Box(Modifier.size(iconSize)) { icon() }
                        if (text.isNotBlank()) Spacer(Modifier.width(spacing))
                    }

                    if (text.isNotBlank()) {
                        Text(
                            text = text,
                            style = txtStyle,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    if (icon != null && (iconConfig == IconConfig.End || iconConfig == IconConfig.Both)) {
                        if (text.isNotBlank()) Spacer(Modifier.width(spacing))
                        Box(Modifier.size(iconSize)) { icon() }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun PrimaryButtonPreview() {
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
                PrimaryButton(
                    text = "Label",
                    onClick = { /* */ },
                )

                // 1-2. 아이콘이 앞에 오는 버튼
                PrimaryButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.Start,
                )

                // 1-3. 아이콘이 뒤에 오는 버튼
                PrimaryButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.End,
                )

                // 1-4. 아이콘만 있는 빈 텍스트 없이 버튼 (text="" 로 처리)
                PrimaryButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.Both,
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
                PrimaryButton(
                    text = "Large",
                    onClick = { /* */ },
                    size = BaseButton.Size.Large,
                )
                PrimaryButton(
                    text = "Medium",
                    onClick = { /* */ },
                    size = BaseButton.Size.Medium,
                )
                PrimaryButton(
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
                PrimaryButton(
                    text = "Active",
                    onClick = { /* */ },
                )
                // 3-2. 활성 상태: 다크 그린 배경 (임시로 래핑하지 않으므로 기본 색 사용)
                // 실제로 컬러를 직접 지정하는 API가 없으므로,
                // 배경 색을 바꾸고 싶다면 CustomizableButton.colors(...) 함수를 수정해야 합니다.
                // 여기서는 예시로 두 번째 버튼을 Disabled 상태와 구별하기 위해 enabled=true 그대로 둡니다.
                PrimaryButton(
                    text = "Dark Green",
                    onClick = { /* */ },
                )
                // 3-3. 비활성화 상태
                PrimaryButton(
                    text = "Disabled",
                    onClick = { /* */ },
                    enabled = false,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun OutlinedButtonPreview() {
    PtPtTheme {
        Column(
            modifier = Modifier
                .background(PtPtTheme.color.textBlack)
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
                OutlinedButton(
                    text = "Label",
                    onClick = { /* */ },
                )

                // 1-2. 아이콘이 앞에 오는 버튼
                OutlinedButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.Start,
                )

                // 1-3. 아이콘이 뒤에 오는 버튼
                OutlinedButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.End,
                )

                // 1-4. 아이콘만 있는 빈 텍스트 없이 버튼 (text="" 로 처리)
                OutlinedButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.Both,
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
                OutlinedButton(
                    text = "Large",
                    onClick = { /* */ },
                    size = BaseButton.Size.Large,
                )
                OutlinedButton(
                    text = "Medium",
                    onClick = { /* */ },
                    size = BaseButton.Size.Medium,
                )
                OutlinedButton(
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
                OutlinedButton(
                    text = "Active",
                    onClick = { /* */ },
                )
                // 3-2. 활성 상태: 다크 그린 배경 (임시로 래핑하지 않으므로 기본 색 사용)
                // 실제로 컬러를 직접 지정하는 API가 없으므로,
                // 배경 색을 바꾸고 싶다면 CustomizableButton.colors(...) 함수를 수정해야 합니다.
                // 여기서는 예시로 두 번째 버튼을 Disabled 상태와 구별하기 위해 enabled=true 그대로 둡니다.
                OutlinedButton(
                    text = "Dark Green",
                    isSelected = true,
                    onClick = { /* */ },
                )
                // 3-3. 비활성화 상태
                OutlinedButton(
                    text = "Disabled",
                    onClick = { /* */ },
                    enabled = false,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun SecondaryButtonPreview() {
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
                SecondaryButton(
                    text = "Label",
                    onClick = { /* */ },
                )

                // 1-2. 아이콘이 앞에 오는 버튼
                SecondaryButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.Start,
                )

                // 1-3. 아이콘이 뒤에 오는 버튼
                SecondaryButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.End,
                )

                // 1-4. 아이콘만 있는 빈 텍스트 없이 버튼 (text="" 로 처리)
                SecondaryButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = BaseButton.IconConfig.Both,
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
                SecondaryButton(
                    text = "Large",
                    onClick = { /* */ },
                    size = BaseButton.Size.Large,
                )
                SecondaryButton(
                    text = "Medium",
                    onClick = { /* */ },
                    size = BaseButton.Size.Medium,
                )
                SecondaryButton(
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
                SecondaryButton(
                    text = "Active",
                    onClick = { /* */ },
                )
                // 3-2. 활성 상태: 다크 그린 배경 (임시로 래핑하지 않으므로 기본 색 사용)
                // 실제로 컬러를 직접 지정하는 API가 없으므로,
                // 배경 색을 바꾸고 싶다면 CustomizableButton.colors(...) 함수를 수정해야 합니다.
                // 여기서는 예시로 두 번째 버튼을 Disabled 상태와 구별하기 위해 enabled=true 그대로 둡니다.
                SecondaryButton(
                    text = "Dark Green",
                    onClick = { /* */ },
                )
                // 3-3. 비활성화 상태
                SecondaryButton(
                    text = "Disabled",
                    onClick = { /* */ },
                    enabled = false,
                )
            }
        }
    }
}
