package com.paradise.core.designsystem.component.textfield.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.icon.PtPtIconButton
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.core.designsystem.theme.schema.PtPtTypography

object BaseTextField {
    interface Style {
        val containerColor: Color @Composable get
        val contentColor: Color @Composable get
        val placeholderColor: Color @Composable get
        val borderColor: Color @Composable get
        val borderWidth: Dp
        val cursorColor: Color @Composable get
    }

    object DefaultStyle : Style {
        private val themeColors @Composable get() = PtPtTheme.color

        override val containerColor @Composable get() = themeColors.componentNormal
        override val contentColor @Composable get() = themeColors.textStrong
        override val placeholderColor @Composable get() = themeColors.textAssist
        override val borderColor @Composable get() = themeColors.primaryPressed
        override val borderWidth: Dp = 1.dp
        override val cursorColor @Composable get() = themeColors.primaryPressed
    }

    enum class Size(
        val height: Dp,
        val horizontalPadding: Dp,
        val verticalPadding: Dp,
        val textStyle: (PtPtTypography) -> TextStyle,
        val shape: Shape,
    ) {
        Default(
            height = 56.dp,
            horizontalPadding = 16.dp,
            verticalPadding = 16.dp,
            textStyle = { typography -> typography.body02 },
            shape = RoundedCornerShape(12.dp),
        ),
    }

    enum class HelperTextType {
        None,
        Success,
        Error,
    }
}

@Composable
fun BaseTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    style: BaseTextField.Style = BaseTextField.DefaultStyle,
    size: BaseTextField.Size = BaseTextField.Size.Default,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    helperText: String? = null,
    helperTextType: BaseTextField.HelperTextType = BaseTextField.HelperTextType.None,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val shouldShowClearButton = value.isNotEmpty() && (isFocused || value.isNotEmpty()) && enabled && !readOnly

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(size.height)
                .clip(size.shape)
                .background(
                    color = style.containerColor,
                    shape = size.shape,
                )
                .then(
                    if (isFocused) {
                        Modifier.border(
                            width = style.borderWidth,
                            color = style.borderColor,
                            shape = size.shape,
                        )
                    } else {
                        Modifier
                    },
                ),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = size.horizontalPadding,
                        vertical = size.verticalPadding,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (leadingIcon != null) {
                    CompositionLocalProvider(LocalContentColor provides style.contentColor) {
                        Box(modifier = Modifier.size(24.dp)) {
                            leadingIcon()
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    BasicTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = value,
                        onValueChange = onValueChange,
                        enabled = enabled,
                        readOnly = readOnly,
                        textStyle = size.textStyle(PtPtTheme.typography).copy(
                            color = style.contentColor,
                        ),
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                        singleLine = singleLine,
                        maxLines = maxLines,
                        visualTransformation = visualTransformation,
                        interactionSource = interactionSource,
                        cursorBrush = SolidColor(style.cursorColor),
                    )

                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            modifier = Modifier.fillMaxWidth(),
                            style = size.textStyle(PtPtTheme.typography),
                            color = style.placeholderColor,
                        )
                    }
                }

                AnimatedVisibility(
                    visible = shouldShowClearButton || trailingIcon != null,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Row {
                        if (shouldShowClearButton && trailingIcon == null) {
                            Spacer(modifier = Modifier.width(8.dp))
                            PtPtIconButton(
                                imageVector = PtPtTheme.icon.union,
                                onClick = { onValueChange("") },
                                modifier = Modifier.size(24.dp),
                            )
                        } else if (trailingIcon != null) {
                            Spacer(modifier = Modifier.width(12.dp))
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        if (onTrailingIconClick != null) {
                                            onTrailingIconClick()
                                        }
                                    },
                            ) {
                                trailingIcon()
                            }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = helperText != null && helperTextType != BaseTextField.HelperTextType.None,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            helperText?.let { text ->
                val helperTextColor = when (helperTextType) {
                    BaseTextField.HelperTextType.Success -> PtPtTheme.color.statusPositive
                    BaseTextField.HelperTextType.Error -> PtPtTheme.color.statusDestructive
                    BaseTextField.HelperTextType.None -> Color.Transparent
                }

                Text(
                    text = text,
                    style = PtPtTheme.typography.caption01,
                    color = helperTextColor,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BaseTextFieldPreview() {
    PtPtTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(PtPtTheme.color.backgroundNormal),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
        }
    }
}
