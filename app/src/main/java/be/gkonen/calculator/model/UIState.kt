package be.gkonen.calculator.model

sealed class UIState {
    object Idle: UIState()
    data class ResultDisplay(val result: String): UIState()
    data class Notification(val message: String): UIState()
}