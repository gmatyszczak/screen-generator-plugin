package ui.newscreen.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.newscreen.NewScreenEffect
import ui.newscreen.NewScreenState

abstract class BaseReducer(
    private val state: MutableStateFlow<NewScreenState>,
    private val effect: MutableSharedFlow<NewScreenEffect>,
    private val scope: CoroutineScope
) {

    protected fun pushEffect(newEffect: NewScreenEffect) {
        scope.launch { effect.emit(newEffect) }
    }

    protected fun pushState(transform: NewScreenState.() -> NewScreenState) {
        state.value = transform(state.value)
    }
}
