package com.paradise.ptpt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paradise.ptpt.contract.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _state: MutableStateFlow<AppState> = MutableStateFlow(AppState())
    val state: StateFlow<AppState> = _state
        .onStart {
            moveToAuthTempMethod()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = AppState(),
        )

    private fun moveToAuthTempMethod() {
        viewModelScope.launch {
            // 임시 로직
            delay(3000L)
            _state.update {
                it.copy(
                    authState = AppState.AuthState.Unauthenticated,
                )
            }
        }
    }

    private fun moveToHomeTempMethod() {
        viewModelScope.launch {
            // 임시 로직
            delay(3000L)
            _state.update {
                it.copy(
                    authState = AppState.AuthState.Authenticated,
                )
            }
        }
    }
}
