package ui.newscreen

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.core.Reducer
import ui.core.ViewModel
import javax.inject.Inject
import javax.inject.Provider

class NewScreenViewModel @Inject constructor(
    state: MutableStateFlow<NewScreenState>,
    effect: MutableSharedFlow<NewScreenEffect>,
    actionFlow: MutableSharedFlow<NewScreenAction>,
    reducers: Map<Class<out NewScreenAction>, @JvmSuppressWildcards Provider<Reducer>>,
) : ViewModel<NewScreenState, NewScreenEffect, NewScreenAction>(state, effect, actionFlow, reducers) {

    init {
        launch { actionFlow.emit(NewScreenAction.Init) }
    }
}
