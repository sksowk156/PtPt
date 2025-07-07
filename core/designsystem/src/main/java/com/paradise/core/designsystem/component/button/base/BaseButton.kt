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
    enum class Style {
        Primary,
        Secondary,
        Outline,
    }

    enum class Size(
        val height: Dp,
        val horizontalPadding: Dp,
        val textStyleKey: (PtPtTypography) -> TextStyle,
        val shapeKey: (PtPtShape) -> Shape,
    ) {
        Small(
            height = 32.dp,
            horizontalPadding = 14.dp,
            textStyleKey = { typography -> typography.caption01 },
            shapeKey = { shape -> shape.s },
        ),
        Medium(
            height = 40.dp,
            horizontalPadding = 20.dp,
            textStyleKey = { typography -> typography.body03 },
            shapeKey = { shape -> shape.m },
        ),
        Large(
            height = 48.dp,
            horizontalPadding = 24.dp,
            textStyleKey = { typography -> typography.body01 },
            shapeKey = { shape -> shape.l },
        ),
    }

    enum class IconConfig {
        None,
        Start,
        End,
        Both,
    }

    @Composable
    fun getStyleColors(style: Style): StyleColors {
        val themeColors = PtPtTheme.color
        return when (style) {
            Style.Primary -> StyleColors(
                backgroundEnabled = themeColors.primaryNormal,
                backgroundPressed = themeColors.primaryPressed,
                backgroundDisabled = themeColors.primaryNormal.copy(alpha = 0.4f),
                foregroundEnabled = themeColors.textBlack,
                foregroundPressed = themeColors.textBlack,
                foregroundDisabled = themeColors.textBlack.copy(alpha = 0.4f),
                borderEnabled = Color.Transparent,
                borderPressed = Color.Transparent,
                borderDisabled = Color.Transparent,
                borderWidth = 0.dp,
            )

            Style.Secondary -> StyleColors(
                backgroundEnabled = themeColors.secondaryNormal,
                backgroundPressed = themeColors.secondaryPressed,
                backgroundDisabled = themeColors.secondaryNormal.copy(alpha = 0.3f),
                foregroundEnabled = themeColors.textStrong,
                foregroundPressed = themeColors.textNeutral,
                foregroundDisabled = themeColors.textStrong.copy(alpha = 0.4f),
                borderEnabled = Color.Transparent,
                borderPressed = Color.Transparent,
                borderDisabled = Color.Transparent,
                borderWidth = 0.dp,
            )

            Style.Outline -> StyleColors(
                backgroundEnabled = Color.Transparent,
                backgroundPressed = Color.Transparent,
                backgroundDisabled = Color.Transparent,
                foregroundEnabled = themeColors.textNeutral,
                foregroundPressed = themeColors.primaryPressed,
                foregroundDisabled = themeColors.textNeutral.copy(alpha = 0.4f),
                borderEnabled = themeColors.textAssist,
                borderPressed = themeColors.primaryNormal,
                borderDisabled = themeColors.textAssist.copy(alpha = 0.4f),
                borderWidth = 1.dp,
            )
        }
    }

    data class StyleColors(
        val backgroundEnabled: Color,
        val backgroundPressed: Color,
        val backgroundDisabled: Color,
        val foregroundEnabled: Color,
        val foregroundPressed: Color,
        val foregroundDisabled: Color,
        val borderEnabled: Color,
        val borderPressed: Color,
        val borderDisabled: Color,
        val borderWidth: Dp,
    )
}

@Composable
fun BaseButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: BaseButton.Style = BaseButton.Style.Primary,
    size: BaseButton.Size = BaseButton.Size.Medium,
    enabled: Boolean = true,
    isSelected: Boolean = false,
    iconConfig: BaseButton.IconConfig = BaseButton.IconConfig.None,
    icon: @Composable (() -> Unit)? = null,
) {
    // 1. 상호작용(Pressed) 추적
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // 1-1. "활성된 눌린 상태" 판단
    //    • 일반(Primary/Secondary) 스타일은 실제 누를 때 pressed를 사용
    //    • Outline 스타일은 라디오처럼 isSelected 값으로 "항상 눌린" 모양을 유지
    val isActivePressedState = when (style) {
        BaseButton.Style.Outline -> isSelected
        else -> isPressed
    }

    // 2. 스타일 색상 가져오기
    val styleColors = BaseButton.getStyleColors(style)

    // 3. 상태별 색 결정
    val targetBackgroundColor = when {
        isActivePressedState -> styleColors.backgroundPressed
        !enabled -> styleColors.backgroundDisabled
        else -> styleColors.backgroundEnabled
    }
    val targetForegroundColor = when {
        isActivePressedState -> styleColors.foregroundPressed
        !enabled -> styleColors.foregroundDisabled
        else -> styleColors.foregroundEnabled
    }
    val targetBorderColor = when {
        isActivePressedState -> styleColors.borderPressed
        !enabled -> styleColors.borderDisabled
        else -> styleColors.borderEnabled
    }

    // 4. 애니메이션
    val animatedBackgroundColor by animateColorAsState(
        targetValue = targetBackgroundColor,
        label = "buttonBackgroundAnimation",
    )
    val animatedBorderColor by animateColorAsState(
        targetValue = targetBorderColor,
        label = "buttonBorderAnimation",
    )

    // 5. 텍스트 스타일·Shape·아이콘 크기 계산
    val textStyle = size.textStyleKey(PtPtTheme.typography)
    val buttonShape = size.shapeKey(PtPtTheme.shape)
    val iconSize = textStyle.lineHeight.value.dp
    val spacingBetweenIconAndText = if (icon != null && iconConfig != BaseButton.IconConfig.None && text.isNotBlank()) {
        4.dp
    } else {
        0.dp
    }

    // 6. 실제 레이아웃
    Surface(
        modifier = modifier.height(size.height),
        shape = buttonShape,
        color = animatedBackgroundColor,
        border = if (styleColors.borderWidth > 0.dp) {
            BorderStroke(
                width = styleColors.borderWidth,
                color = animatedBorderColor,
            )
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
            CompositionLocalProvider(LocalContentColor provides targetForegroundColor) {
                // Start Icon
                if (icon != null && (iconConfig == BaseButton.IconConfig.Start || iconConfig == BaseButton.IconConfig.Both)) {
                    Box(modifier = Modifier.size(iconSize)) {
                        icon()
                    }
                    if (text.isNotBlank()) {
                        Spacer(modifier = Modifier.width(spacingBetweenIconAndText))
                    }
                }

                // Text
                if (text.isNotBlank()) {
                    Text(
                        text = text,
                        style = textStyle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                // End Icon
                if (icon != null && (iconConfig == BaseButton.IconConfig.End || iconConfig == BaseButton.IconConfig.Both)) {
                    if (text.isNotBlank()) {
                        Spacer(modifier = Modifier.width(spacingBetweenIconAndText))
                    }
                    Box(modifier = Modifier.size(iconSize)) {
                        icon()
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
