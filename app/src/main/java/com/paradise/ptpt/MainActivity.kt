package com.paradise.ptpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.ptpt.ui.PtPtApp
import com.paradise.ptpt.ui.rememberAppNavState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appNavState = rememberAppNavState()

            PtPtTheme {
                PtPtApp(
                    appNavState = appNavState,
                )
            }
        }
    }
}
