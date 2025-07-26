package com.paradise.feature.auth.component

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberAuthScreenStateHolder(): AuthScreenStateHolder {
    return remember { AuthScreenStateHolderImpl() }
}

@Stable
interface AuthScreenStateHolder {
    val logoOffset: Float
    val buttonAlpha: Float

    suspend fun animate(targetOffsetPx: Float)
}

class AuthScreenStateHolderImpl : AuthScreenStateHolder {
    private val _logoOffset = Animatable(0f)
    override val logoOffset: Float by _logoOffset.asState()

    private val _buttonAlpha = Animatable(0f)
    override val buttonAlpha: Float by _buttonAlpha.asState()

    override suspend fun animate(targetOffsetPx: Float) {
        coroutineScope {
            launch {
                _logoOffset.animateTo(
                    targetValue = targetOffsetPx,
                    animationSpec = tween(
                        durationMillis = LOGO_ANIM_DURATION,
                        easing = FastOutSlowInEasing,
                    ),
                )
            }
            launch {
                _buttonAlpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = BUTTON_ANIM_DURATION,
                        delayMillis = BUTTON_ANIM_DELAY,
                        easing = LinearOutSlowInEasing,
                    ),
                )
            }
        }
    }

    companion object {
        const val LOGO_OFFSET_RATIO = 0.25f
        const val LOGO_ANIM_DURATION = 800
        const val BUTTON_ANIM_DURATION = 600
        const val BUTTON_ANIM_DELAY = 400
    }
}

@Composable
internal fun AuthScreen(
    screenStateHolder: AuthScreenStateHolder = rememberAuthScreenStateHolder(),
    onLoginClick: () -> Unit = {},
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind { drawRect(color = Color(0xFF1C1C1F)) },
    ) {
        val targetOffsetPx = with(LocalDensity.current) {
            maxHeight.toPx() * AuthScreenStateHolderImpl.LOGO_OFFSET_RATIO
        }

        LaunchedEffect(Unit) {
            screenStateHolder.animate(-targetOffsetPx)
        }

        AuthLogo(
            offset = screenStateHolder.logoOffset,
            modifier = Modifier.align(Alignment.Center),
        )

        LoginButton(
            onClick = onLoginClick,
            alpha = screenStateHolder.buttonAlpha,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(bottom = 60.dp),
        )
    }
}

@Composable
private fun AuthLogo(
    offset: Float,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(R.drawable.ic_ptpt_logo),
        contentDescription = null,
        modifier = modifier
            .graphicsLayer { translationY = offset }
            .size(width = 120.dp, height = 58.dp),
    )
}

@Composable
private fun LoginButton(
    onClick: () -> Unit,
    alpha: Float,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.graphicsLayer { this.alpha = alpha },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
        ),
    ) {
        Text(
            text = "임시 로그인 버튼",
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp),
        )
    }
}

// Preview용 Mock StateHolder
private class PreviewAuthScreenStateHolder(
    override val logoOffset: Float = 0f,
    override val buttonAlpha: Float = 0f,
) : AuthScreenStateHolder {
    override suspend fun animate(targetOffsetPx: Float) {
    }
}

private class AuthScreenStateProvider : PreviewParameterProvider<AuthScreenStateHolder> {
    override val values = sequenceOf(
        // 초기 상태
        PreviewAuthScreenStateHolder(
            logoOffset = 0f,
            buttonAlpha = 0f,
        ),
        // 애니메이션 중간 상태
        PreviewAuthScreenStateHolder(
            logoOffset = -50f,
            buttonAlpha = 0.5f,
        ),
        // 애니메이션 완료 상태
        PreviewAuthScreenStateHolder(
            logoOffset = -100f,
            buttonAlpha = 1f,
        ),
    )
}

// 기본 Preview
@Preview(name = "Auth Screen - Default")
@Composable
fun AuthScreenPreview() {
    AuthScreen(
        onLoginClick = {},
    )
}

@Preview(name = "Auth Screen - States", showBackground = true)
@Composable
fun AuthScreenStatesPreview(
    @PreviewParameter(AuthScreenStateProvider::class)
    stateHolder: AuthScreenStateHolder,
) {
    AuthScreen(
        screenStateHolder = stateHolder,
        onLoginClick = {},
    )
}

// 다크모드 Preview
@Preview(
    name = "Auth Screen - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun AuthScreenDarkPreview() {
    AuthScreen(
        screenStateHolder = PreviewAuthScreenStateHolder(
            logoOffset = -100f,
            buttonAlpha = 1f,
        ),
        onLoginClick = {},
    )
}

// 작은 화면 Preview
@Preview(
    name = "Auth Screen - Small Screen",
    device = "spec:width=320dp,height=640dp,dpi=160",
)
@Composable
fun AuthScreenSmallPreview() {
    AuthScreen(
        screenStateHolder = PreviewAuthScreenStateHolder(
            logoOffset = -80f,
            buttonAlpha = 1f,
        ),
        onLoginClick = {},
    )
}

// 태블릿 Preview
@Preview(
    name = "Auth Screen - Tablet",
    device = "spec:width=800dp,height=1280dp,dpi=240",
)
@Composable
fun AuthScreenTabletPreview() {
    AuthScreen(
        screenStateHolder = PreviewAuthScreenStateHolder(
            logoOffset = -150f,
            buttonAlpha = 1f,
        ),
        onLoginClick = {},
    )
}

// Interactive Preview (Android Studio Electric Eel 이상)
@Preview(name = "Auth Screen - Interactive")
@Composable
fun AuthScreenInteractivePreview() {
    var clickCount by remember { mutableIntStateOf(0) }

    Column {
        Text(
            text = "Click count: $clickCount",
            modifier = Modifier.padding(16.dp),
            color = Color.White,
        )

        AuthScreen(
            screenStateHolder = PreviewAuthScreenStateHolder(
                logoOffset = -100f,
                buttonAlpha = 1f,
            ),
            onLoginClick = { clickCount++ },
        )
    }
}

// 개별 컴포넌트 Preview
@Preview(name = "Auth Logo")
@Composable
fun AuthLogoPreview() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color(0xFF1C1C1F)),
    ) {
        AuthLogo(
            offset = -50f,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Preview(name = "Login Button")
@Composable
fun LoginButtonPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .background(Color(0xFF1C1C1F)),
    ) {
        LoginButton(
            onClick = {},
            alpha = 1f,
        )
    }
}
