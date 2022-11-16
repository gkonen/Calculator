package be.gkonen.calculator.model

sealed class UIState {
    object Idle: UIState()
    data class Notification(val message: String): UIState()
}