package com.paradise.ptpt.contract

data class AppState(
    val authState: AuthState = AuthState.Initializing,
) {
    sealed interface AuthState {
        data object Initializing : AuthState

        data object Unauthenticated : AuthState

        data object Authenticated : AuthState
    }
}
