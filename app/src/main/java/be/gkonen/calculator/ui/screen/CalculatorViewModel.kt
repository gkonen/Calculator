package be.gkonen.calculator.ui.screen

import androidx.lifecycle.ViewModel
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

    fun onEvent(event: UIEvent) {
        when(event) {
            is UIEvent.ButtonPressed -> {
                _uiState.value = UIState.Notification(
                    "Vous avez appuyé sur ${event.button.name} "
                )
            }
        }

    }

}