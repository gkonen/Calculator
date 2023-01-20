package be.gkonen.calculator.model

sealed class UIState {
    data class Idle(val oldResult: String = ""): UIState()
    data class ResultDisplay(val result: String): UIState()
    data class Notification(val message: String): UIState()
}