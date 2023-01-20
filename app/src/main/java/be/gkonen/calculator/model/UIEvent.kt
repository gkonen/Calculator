package be.gkonen.calculator.model

import be.gkonen.calculator.domain.KeyboardHelper

sealed class UIEvent {
    data class ButtonPressed(val button: KeyboardHelper.ButtonConfig) : UIEvent()
    data class OldResultPressed(val oldResult: String): UIEvent()
}