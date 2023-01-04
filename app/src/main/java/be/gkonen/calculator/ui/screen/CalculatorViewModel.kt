package be.gkonen.calculator.ui.screen

import androidx.lifecycle.ViewModel
import be.gkonen.calculator.domain.KeyboardHelper
import be.gkonen.calculator.model.UIEvent
import be.gkonen.calculator.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(): ViewModel() {

    private var _uiState = MutableStateFlow<UIState>(UIState.Idle)
    val uiState : StateFlow<UIState> = _uiState

    private var _information = MutableStateFlow("")
    val information : StateFlow<String> = _information

    private var firstMember = MemberOperation("")
    private var operation: String = ""
    private var secondMember = MemberOperation("")


    fun onEvent(event: UIEvent) {
        when(event) {
            is UIEvent.ButtonPressed -> {
                manageButton(event.button)
                if(event.button.type != KeyboardHelper.ButtonType.VALUE) {
                    _uiState.value = UIState.Notification(
                        "Vous avez appuyé sur ${event.button.name}"
                    )
                }
            }
        }

    }

    private fun updateInformation(newValue: String = "$firstMember $operation $secondMember") {
        _information.value = newValue
    }

    private fun clear() {
        firstMember.clear()
        secondMember.clear()
        operation = ""
        updateInformation("")
    }

    private fun manageButton(config : KeyboardHelper.ButtonConfig) {
        when(config) {
            KeyboardHelper.ButtonConfig.AC -> clear()

            KeyboardHelper.ButtonConfig.PLUS_MINUS -> {
                if(operation.isEmpty()) {
                    firstMember.inverse()
                } else {
                    secondMember.inverse()
                }

            }
            KeyboardHelper.ButtonConfig.PERCENT -> {

            }
            KeyboardHelper.ButtonConfig.ADD -> {
                operation = "+"
            }
            KeyboardHelper.ButtonConfig.MINUS -> {
                operation = "-"
            }
            KeyboardHelper.ButtonConfig.DIVIDE -> {
                operation = "/"
            }
            KeyboardHelper.ButtonConfig.MULTIPLY -> {
                operation = "*"
            }
            KeyboardHelper.ButtonConfig.EQUAL -> {

            }
            else -> {
                if (config.type == KeyboardHelper.ButtonType.VALUE) addCharacter(config.value)
            }
        }
        updateInformation()
    }

    private fun addCharacter(value: String?) {
        if (value == null) return
        if (operation.isEmpty()) {
            firstMember += value
        } else {
            secondMember += value
        }

    }

    private class MemberOperation(var content: String) {

        private var isPositive = true

        fun clear() {
            isPositive = true
            content = ""
        }

        fun inverse() {
            isPositive = !isPositive
        }

        operator fun plusAssign(value: String) {
            content += if(value == "0") {
                if(content.isBlank()) "" else value
            } else if(value == ".") {
                if(content.isBlank()) "0." else "."
            } else {
                value
            }
        }

        override fun toString(): String {
            return if(!isPositive) "-$content" else content
        }
    }

}