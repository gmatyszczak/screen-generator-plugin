package ui.newscreen

import ui.newscreen.reducer.CategoryIndexChangedReducer
import ui.newscreen.reducer.InitReducer
import ui.newscreen.reducer.OkClickedReducer
import javax.inject.Inject

class NewScreenViewModel @Inject constructor(
    initReducer: InitReducer,
    private val okClickedReducer: OkClickedReducer,
    private val categoryIndexChangedReducer: CategoryIndexChangedReducer
) {

    init {
        initReducer()
    }

    fun reduce(action: NewScreenAction) = when (action) {
        is NewScreenAction.OkClicked -> okClickedReducer(
            action.packageName,
            action.screenName,
            action.androidComponentIndex,
            action.module,
            action.category,
            action.customVariablesMap
        )
        is NewScreenAction.CategoryIndexChanged -> categoryIndexChangedReducer(action.index)
    }
}