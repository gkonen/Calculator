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

    fun onEvent(event: UIEvent) {
        when(event) {
            is UIEvent.ButtonPressed -> {
                manageButton(event.button)
                _uiState.value = UIState.Notification(
                    "Vous avez appuyé sur ${event.button.name} : ${_information.value} "
                )
            }
        }

    }

    private fun manageButton(config : KeyboardHelper.ButtonConfig) {
        when(config) {
            KeyboardHelper.ButtonConfig.AC -> _information.value = ""
            KeyboardHelper.ButtonConfig.POINT -> {
                if(_information.value.isBlank()) {
                    _information.value = "0."
                } else if("." !in _information.value) {
                    _information.value += "."
                }
            }
            KeyboardHelper.ButtonConfig.PLUS_MINUS -> {

            }
            KeyboardHelper.ButtonConfig.PERCENT -> {

            }
            KeyboardHelper.ButtonConfig.ADD -> {

            }
            KeyboardHelper.ButtonConfig.MINUS -> {

            }
            KeyboardHelper.ButtonConfig.DIVIDE -> {

            }
            KeyboardHelper.ButtonConfig.MULTIPLY -> {

            }
            KeyboardHelper.ButtonConfig.EQUAL -> {

            }
            KeyboardHelper.ButtonConfig.B0 -> _information.value += "0"
            KeyboardHelper.ButtonConfig.B1 -> _information.value += "1"
            KeyboardHelper.ButtonConfig.B2 -> _information.value += "2"
            KeyboardHelper.ButtonConfig.B3 -> _information.value += "3"
            KeyboardHelper.ButtonConfig.B4 -> _information.value += "4"
            KeyboardHelper.ButtonConfig.B5 -> _information.value += "5"
            KeyboardHelper.ButtonConfig.B6 -> _information.value += "6"
            KeyboardHelper.ButtonConfig.B7 -> _information.value += "7"
            KeyboardHelper.ButtonConfig.B8 -> _information.value += "8"
            KeyboardHelper.ButtonConfig.B9 -> _information.value += "9"
        }
    }

}