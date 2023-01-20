package be.gkonen.calculator.ui.screen

import androidx.lifecycle.ViewModel
import be.gkonen.calculator.domain.KeyboardHelper
import be.gkonen.calculator.domain.MemberOperation
import be.gkonen.calculator.model.UIEvent
import be.gkonen.calculator.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(): ViewModel() {

    private var _uiState = MutableStateFlow<UIState>(UIState.Idle())
    val uiState : StateFlow<UIState> = _uiState.asStateFlow()

    private var _information = MutableStateFlow("")
    val information : StateFlow<String> = _information.asStateFlow()

    private var _oldResult = MutableStateFlow("")
    val oldResult : StateFlow<String>         = _oldResult.asStateFlow()

    private var firstMember = MemberOperation("")
    private var operation: String = ""
    private var secondMember = MemberOperation("")

    fun onEvent(event: UIEvent) {
        when(event) {
            is UIEvent.ButtonPressed -> {
                if(_uiState.value is UIState.ResultDisplay) {
                    _oldResult.value = (_uiState.value as UIState.ResultDisplay).result
                    clear()
                    _uiState.value = UIState.Idle()
                    if(event.button.type == KeyboardHelper.ButtonType.VALUE) {
                        manageButton(event.button)
                    } else if(event.button.type == KeyboardHelper.ButtonType.OPERATOR) {
                        firstMember = MemberOperation(_oldResult.value)
                        manageButton(event.button)
                    }
                } else {
                    manageButton(event.button)
                    if(event.button.type == KeyboardHelper.ButtonType.SPECIAL) {
                        _uiState.value = UIState.Notification(
                            "Vous avez appuyé sur ${event.button.name}"
                        )
                    }
                }
            }
            is UIEvent.OldResultPressed -> {
                var newValue = event.oldResult
                if(_uiState.value is UIState.ResultDisplay) {
                    newValue = (_uiState.value as UIState.ResultDisplay).result
                    _oldResult.value = newValue
                    firstMember = MemberOperation(newValue)
                    clear()
                }
                if(operation.isEmpty()) {
                    firstMember = MemberOperation(newValue)
                } else {
                    secondMember = MemberOperation(newValue)
                }
                updateInformation()
            }
        }
    }

    private fun compute(): String {
        return if(firstMember.isNotEmpty() && secondMember.isNotEmpty()) {
            when (operation) {
                "+" -> {
                    (firstMember + secondMember).toString()
                }
                "-" -> {
                    (firstMember - secondMember ).toString()
                }
                "*" -> {
                     (firstMember * secondMember).toString()
                }
                "/" -> {
                     (firstMember / secondMember).toString()
                }
                else -> "NaN"
            }
        } else {
            ""
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
                _uiState.value = UIState.ResultDisplay(compute())
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

}