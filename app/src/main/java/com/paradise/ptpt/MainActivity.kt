package com.paradise.ptpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.ptpt.contract.AppState
import com.paradise.ptpt.ui.PtPtApp
import com.paradise.ptpt.ui.rememberAppNavState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        splashScreen.setKeepOnScreenCondition {
            viewModel.state.value.authState is AppState.AuthState.Initializing
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                }
            }
        }

        setContent {
            val appState by viewModel.state.collectAsStateWithLifecycle()
            val authState = appState.authState
            val appNavState = rememberAppNavState()

            PtPtTheme {
                if (authState !is AppState.AuthState.Initializing) {
                    PtPtApp(
                        authState = authState,
                        appNavState = appNavState,
                    )
                }
            }
        }
    }
}
