package com.paradise.core.designsystem.component.textfield

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.textfield.base.BaseTextField
import com.paradise.core.designsystem.theme.PtPtTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Stable
class PtPtTextFieldState(initialValue: String = "") {
    var value by mutableStateOf(initialValue)
        private set

    var currentFeedback by mutableStateOf<Feedback?>(null)
        private set

    fun updateValue(newValue: String) {
        value = newValue
        currentFeedback = null
    }

    fun setSubmitFeedback(
        text: String?,
        type: BaseTextField.HelperTextType = BaseTextField.HelperTextType.Error,
    ) {
        currentFeedback = text?.let { Feedback(it, type) }
    }

    fun applyValidationRule(rule: Rule) {
        val isValid = rule.validate(value)

        currentFeedback = when {
            isValid && rule.successMessage.isNotEmpty() ->
                Feedback(rule.successMessage, BaseTextField.HelperTextType.Success)

            !isValid && rule.errorMessage.isNotEmpty() ->
                Feedback(rule.errorMessage, BaseTextField.HelperTextType.Error)

            else -> null
        }
    }

    fun clearValue() {
        value = ""
        currentFeedback = null
    }

    fun isValid(): Boolean = value.isNotEmpty() && currentFeedback?.type != BaseTextField.HelperTextType.Error

    data class Feedback(
        val text: String,
        val type: BaseTextField.HelperTextType,
    )

    data class Rule(
        val validate: (String) -> Boolean,
        val successMessage: String = "",
        val errorMessage: String = "",
    )

    companion object {
        val Saver: Saver<PtPtTextFieldState, *> = listSaver(
            save = { listOf(it.value) },
            restore = { values -> PtPtTextFieldState(values.firstOrNull().orEmpty()) },
        )
    }
}

@Composable
fun rememberPtPtTextFieldState(initialValue: String = ""): PtPtTextFieldState = rememberSaveable(saver = PtPtTextFieldState.Saver) {
    PtPtTextFieldState(initialValue)
}

@Composable
fun PtPtTextField(
    state: PtPtTextFieldState,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: (@Composable () -> Unit)? = null,
    realtimeRule: PtPtTextFieldState.Rule? = null,
    debounceDelay: Long = 300L,
    onSubmit: ((String) -> Unit)? = null,
) {
    var debounceJob by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.value, realtimeRule, debounceDelay) {
        debounceJob?.cancel()
        if (realtimeRule == null || state.value.isEmpty()) {
            state.setSubmitFeedback(null)
        } else {
            debounceJob = scope.launch {
                delay(debounceDelay)
                state.applyValidationRule(realtimeRule)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { debounceJob?.cancel() }
    }

    BaseTextField(
        value = state.value,
        onValueChange = state::updateValue,
        modifier = modifier,
        placeholder = placeholder,
        style = BaseTextField.DefaultStyle,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                if (state.isValid()) onSubmit?.invoke(state.value)
            },
        ),
        visualTransformation = visualTransformation,
        leadingIcon = leadingIcon,
        trailingIcon = null,
        onTrailingIconClick = if (state.value.isNotEmpty() && enabled && !readOnly) {
            { state.clearValue() }
        } else {
            null
        },
        helperText = state.currentFeedback?.text,
        helperTextType = state.currentFeedback?.type ?: BaseTextField.HelperTextType.None,
    )
}

@Composable
fun PtPtTextField(
    modifier: Modifier = Modifier,
    initialValue: String = "",
    placeholder: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: (@Composable () -> Unit)? = null,
    realtimeRule: PtPtTextFieldState.Rule? = null,
    debounceDelay: Long = 300L,
    onSubmit: ((String) -> Unit)? = null,
) {
    val state = rememberPtPtTextFieldState(initialValue)

    PtPtTextField(
        state = state,
        modifier = modifier,
        placeholder = placeholder,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        leadingIcon = leadingIcon,
        realtimeRule = realtimeRule,
        debounceDelay = debounceDelay,
        onSubmit = onSubmit,
    )
}

@Preview(showBackground = true, name = "State Variations")
@Composable
private fun PtPtTextFieldStatesPreview() {
    PtPtTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(PtPtTheme.color.backgroundNormal),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("TextField 상태별 예시", style = PtPtTheme.typography.body01)

            // 1. 기본 상태 -------------------------------------------------
            Text("기본 상태", style = PtPtTheme.typography.body02)
            PtPtTextField(placeholder = "이메일을 입력하세요")

            // 2. 실시간 검증 – 에러 --------------------------------------
            Text("실시간 검증 - 에러", style = PtPtTheme.typography.body02)
            PtPtTextField(
                placeholder = "이메일을 입력하세요",
                realtimeRule = PtPtTextFieldState.Rule(
                    validate = { it.contains("@") },
                    errorMessage = "올바른 이메일 형식을 입력하세요",
                    successMessage = "이메일 형식입니다.",
                ),
            )

            Text("서버 검증 - 성공", style = PtPtTheme.typography.body02)
            var flag by remember { mutableStateOf(false) }
            val successState = rememberPtPtTextFieldState("user@example.com")
            LaunchedEffect(flag) {
                if (flag) {
                    successState.setSubmitFeedback(
                        "사용 가능한 이메일입니다",
                        BaseTextField.HelperTextType.Success,
                    )
                    flag = false
                }
            }

            PtPtTextField(
                state = successState,
                placeholder = "이메일을 입력하세요",
                onSubmit = {
                    flag = true
                },
            )

            // 4. 서버 검증 – 에러 ------------------------------------------
            Text("서버 검증 - 에러", style = PtPtTheme.typography.body02)
            var flag2 by remember { mutableStateOf(false) }
            val duplicateState = rememberPtPtTextFieldState("duplicate@example.com")
            LaunchedEffect(flag2) {
                if (flag2) {
                    duplicateState.setSubmitFeedback(
                        "이미 사용 중인 이메일입니다",
                        BaseTextField.HelperTextType.Error,
                    )
                    flag2 = false
                }
            }

            PtPtTextField(
                state = duplicateState,
                placeholder = "이메일을 입력하세요",
                onSubmit = {
                    flag2 = true
                },
            )

            // 5. 비활성화 상태 -------------------------------------------
            Text("비활성화", style = PtPtTheme.typography.body02)
            PtPtTextField(placeholder = "비활성화된 필드", enabled = false)
        }
    }
}

@Preview(showBackground = true, name = "Interactive Email Validation")
@Composable
private fun PtPtTextFieldInteractivePreview() {
    PtPtTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(PtPtTheme.color.backgroundNormal),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("인터랙티브 이메일 검증", style = PtPtTheme.typography.body01)
            val emailState = rememberPtPtTextFieldState()
            var lastSubmittedEmail by remember { mutableStateOf<String?>(null) }
            PtPtTextField(
                state = emailState,
                placeholder = "이메일을 입력하고 엔터를 누르세요",
                realtimeRule = PtPtTextFieldState.Rule(
                    validate = { Patterns.EMAIL_ADDRESS.matcher(it).matches() },
                    errorMessage = "올바른 이메일 형식을 입력하세요",
                ),
                onSubmit = { email ->
                    lastSubmittedEmail = email
                    when {
                        email == "test@example.com" -> emailState.setSubmitFeedback(
                            "이미 사용 중인 이메일입니다",
                            BaseTextField.HelperTextType.Error,
                        )

                        email.endsWith("@blocked.com") -> emailState.setSubmitFeedback(
                            "사용할 수 없는 도메인입니다",
                            BaseTextField.HelperTextType.Error,
                        )

                        else -> emailState.setSubmitFeedback(
                            "사용 가능한 이메일입니다",
                            BaseTextField.HelperTextType.Success,
                        )
                    }
                },
            )
            lastSubmittedEmail?.let {
                Text(
                    "마지막 제출: $it",
                    style = PtPtTheme.typography.body03,
                    color = PtPtTheme.color.secondaryNormal,
                )
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Text("테스트 시나리오:", style = PtPtTheme.typography.body02)
            Text("• test@example.com → 중복 이메일", style = PtPtTheme.typography.body03)
            Text("• *@blocked.com → 차단된 도메인", style = PtPtTheme.typography.body03)
            Text("• 그 외 유효한 이메일 → 사용 가능", style = PtPtTheme.typography.body03)
        }
    }
}

@Preview(showBackground = true, name = "Password Change")
@Composable
private fun PasswordChangePreview() {
    PtPtTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(PtPtTheme.color.backgroundNormal),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("비밀번호 변경", style = PtPtTheme.typography.body01)

            val currentPwState = rememberPtPtTextFieldState()
            val newPwState = rememberPtPtTextFieldState()
            var isPwVerified by remember { mutableStateOf(false) }

            PtPtTextField(
                state = currentPwState,
                placeholder = "현재 비밀번호",
                visualTransformation = PasswordVisualTransformation(),
                onSubmit = { password ->
                    if (password == "correct") {
                        currentPwState.setSubmitFeedback(
                            "비밀번호가 확인되었습니다",
                            BaseTextField.HelperTextType.Success,
                        )
                        isPwVerified = true
                    } else {
                        currentPwState.setSubmitFeedback(
                            "비밀번호가 일치하지 않습니다",
                            BaseTextField.HelperTextType.Error,
                        )
                        isPwVerified = false
                    }
                },
            )

            PtPtTextField(
                state = newPwState,
                placeholder = "새 비밀번호",
                visualTransformation = PasswordVisualTransformation(),
                enabled = isPwVerified,
                realtimeRule = PtPtTextFieldState.Rule(
                    validate = { it.length >= 8 },
                    errorMessage = "8자 이상 입력하세요",
                ),
            )

            Text(
                "테스트: 현재 비밀번호에 'correct' 입력",
                style = PtPtTheme.typography.body03,
                color = PtPtTheme.color.secondaryNormal,
            )
        }
    }
}
